package com.ntk.epcm.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ntk.epcm.model.Customer;
import com.ntk.epcm.service.CustomerService;

@Component
public class CustomerConverter implements Converter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerConverter.class);
	@Inject
	CustomerService customerService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent ui, String customer) {
		if (!StringUtils.isEmpty(customer)) {
			try {
				int customer_id = Integer.valueOf(customer.split(" - ")[0]);
				return customerService.findById(customer_id);
			} catch (Exception e) {
				LOGGER.error("error while trying find customer by id", e);
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent ui, Object customer) {
		if(customer instanceof Customer){
			Customer c = (Customer) customer;
			if(((Customer) customer).getCustomer_id()==0)
				return null;
			return String.format("%s - %s %s", c.getCustomer_id(),c.getFirstName(), c.getLastName() );
		}
		return null;
	}

}
