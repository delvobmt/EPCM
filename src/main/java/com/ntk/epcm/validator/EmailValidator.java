package com.ntk.epcm.validator;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ntk.epcm.service.IAccountService;

@Component
@Scope("session")
public class EmailValidator implements Validator {
	final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Inject
	IAccountService accountService;

	Pattern pattern = Pattern
			.compile("^[_A-Za-z0-9-]+(\\." + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String email = value.toString();
		String summary = "";
		String detail = "";
		if(!pattern.matcher(email).matches()){
			summary = "Username is invalid format";
		}else {
			try {
				LOGGER.debug("account service is {}", accountService);
				if (accountService.checkExistenceEmail(email))
					summary = "Email is used by another";
			} catch (Exception e) {
				LOGGER.error("error with validate username", e);
				summary = "Service is not working, please contact with admin.";
			}
		}
		detail = StringUtils.isEmpty(detail) ? summary : detail;
		if (!StringUtils.isEmpty(summary)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
			throw new ValidatorException(msg);
		}
	}

}
