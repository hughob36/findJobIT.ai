package com.scraper.service.scraper_service.strategy;

public interface IPortalScraper {

    String getBaseUrl();
    String getTarjetaSelector();
    String getTituloSelector();
    String getEmpresaSelector();
    boolean soportado(String url);
}
