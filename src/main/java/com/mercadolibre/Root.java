package com.mercadolibre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"paging", "product_results", "buy_box_filters","sort","available_sorts","filters","available_filters","backend_info","logs","times","back_parameters_call","experiments_data"})
public class Root{
    public String site_id;
    public String country_default_time_zone;
    public String query;
    public List<Result> results;
}

