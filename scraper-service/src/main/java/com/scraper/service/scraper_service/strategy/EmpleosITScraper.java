package com.scraper.service.scraper_service.strategy;

import org.springframework.stereotype.Component;

@Component
public class EmpleosITScraper implements IPortalScraper{

    @Override
    public String getBaseUrl() {
        return "https://www.empleosit.com.ar/search-results-jobs/?action=search&listing_type%5Bequal%5D=Job&view=list&sorting_field=priority&sorting_order=DESC";
    }

    @Override
    public String getTarjetaSelector() {
        return ".listing-right ";
    }

    @Override
    public String getTituloSelector() {
        return ".listing-title";
    }

    @Override
    public String getEmpresaSelector() {
        return ".captions-field";
    }

    @Override
    public boolean soportado(String url) {
        return url.contains("empleosit.com.ar");
    }
}
