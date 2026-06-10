package com.scraper.service.scraper_service.service;

import com.scraper.service.scraper_service.strategy.IPortalScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ScraperEngineService {

    private final List<IPortalScraper> scrapers;

    @Autowired
    public ScraperEngineService(List<IPortalScraper> scrapers) {
        this.scrapers = scrapers;
    }


    public void ejecutarScraping(String urlObjetivo) {

        // 1. Buscar qué estrategia (scraper) sabe leer esta URL
        IPortalScraper scraperConfig = scrapers.stream()
                .filter(s -> s.soportado(urlObjetivo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Error: Ningún scraper configurado soporta la URL: " + urlObjetivo));

        System.out.println("Portal detectado con éxito. Iniciando motor de extracción...");
        System.out.println("===============================================================");

        int pagina = 1;
        boolean hayMasPaginas = true;

        while (hayMasPaginas) {

            String urlConPagina = scraperConfig.getBaseUrl() + "&page=" + pagina;

            System.out.println("=> Extrayendo Página " + pagina + "...");
            System.out.println("Conectando a: " + urlConPagina);

            try {
                // 2. Descargar el HTML de la página actual
                Document doc = Jsoup.connect(urlConPagina)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                        .timeout(30000)
                        .get();

                // 3. Buscar las tarjetas usando el selector de la estrategia
                Elements tarjetasEmpleo = doc.select(scraperConfig.getTarjetaSelector());

                // Condicion de corte: Si la pagina viene vacia, frena el bucle
                if (tarjetasEmpleo.isEmpty()) {
                    System.out.println("No se encontraron más ofertas en la página " + pagina + ". Fin del paginado.");
                    hayMasPaginas = false;
                    break;
                }

                System.out.println("Ofertas encontradas en página " + pagina + ": " + tarjetasEmpleo.size());
                System.out.println("---------------------------------------------------------------");

                // 4. Iterar y extraer datos usando los selectores específicos de la estrategia
                for (Element tarjeta : tarjetasEmpleo) {
                    String titulo = tarjeta.select(scraperConfig.getTituloSelector()).text();
                    String empresa = tarjeta.select(scraperConfig.getEmpresaSelector()).text();

                    if (!titulo.isEmpty()) {
                        System.out.println("[PUESTO]: " + titulo);
                        System.out.println("[EMPRESA]: " + empresa);
                        System.out.println("---------------------------------------------------------------");

                        // TODO: Aquí reemplazaremos estos prints por el mapeo a la Entidad
                        // y el guardado en la base de datos: repository.save(oferta);
                    }
                }
                pagina++;
                // Pausa de seguridad (3 segundos) para evitar bloqueos de IP
                Thread.sleep(3000);

            } catch (IOException e) {
                System.err.println("Error de conexión en la página " + pagina + ": " + e.getMessage());
                hayMasPaginas = false; // Detiene el flujo para revisar el error
            } catch (InterruptedException e) {
                System.err.println("El proceso fue interrumpido de forma abrupta: " + e.getMessage());
                Thread.currentThread().interrupt();
                hayMasPaginas = false;
            }
        }

        System.out.println("===============================================================");
        System.out.println("Proceso de scraping terminado. Páginas recorridas: " + (pagina - 1));
    }

}
