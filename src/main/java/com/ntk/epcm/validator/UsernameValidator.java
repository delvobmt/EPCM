package com.ntk.epcm.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;

import com.ntk.epcm.service.IAccountService;

@Component
@RequestScope
@FacesValidator
public class UsernameValidator implements Validator {
	final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Inject
	IAccountService accountService;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String username = value.toString();
		String summary = "";
		String detail = "";
		if (username.length() < 4) {
			summary = "Username is too short";
		} else if (username.length() > 32) {
			summary = "Username is too long";
		} else {
			try {
				LOGGER.debug("account service is {}", accountService);
				if (accountService.checkExistenceUsername(username))
					summary = "Username is existed";
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
