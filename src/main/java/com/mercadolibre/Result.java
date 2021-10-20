package com.mercadolibre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"variations_data","offer_score","offer_share"})
public class Result{
    public String id;
    public String site_id;
    public String title;
    public Seller seller;
    public int price;
    public Price prices;
    public Object sale_price;
    public String currency_id;
    public int available_quantity;
    public int sold_quantity;
    public String buying_mode;
    public String listing_type_id;
    public Date stop_time;
    public String condition;
    public String permalink;
    public String thumbnail;
    public String thumbnail_id;
    public boolean accepts_mercadopago;
    public Object installments;
    public Address address;
    public Shipping shipping;
    public SellerContact seller_contact;
    public Location location;
    public SellerAddress seller_address;
    public List<Attribute> attributes;
    public Object original_price;
    public String category_id;
    public Object official_store_id;
    public String domain_id;
    public List<SaleTerm> sale_terms;
    public Object variation_id;
    public String vertical;
    public List<String> variation_filters;
    public String image_ratio;
    public String international_delivery_mode;

    public int colors_qty;
    public int pictures_qty;
    public boolean has_variations;
    public Reviews reviews;
    public String catalog_product_id;
    public List<String> tags;
    public int order_backend;
    public boolean use_thumbnail_id;
}
