package com.diccionariobd.diccionariodatos.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.diccionariobd.diccionariodatos.model.MensajeErrores;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

/**
 * Handle Persephone exceptions and display them in error popups
 */
public class UIErrorHandler implements ErrorHandler {

	private static final long serialVersionUID = 7441835079387513134L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UIErrorHandler.class);

	@Override
    public void error(com.vaadin.server.ErrorEvent event) {
		
		LOGGER.error("Por el metodo error");

		// Loop through the exception stack
		for (Throwable t = event.getThrowable(); t != null; t = t.getCause()) {

			// Try to get a persephone exception
			boolean exceptionHandled = handlePersephoneExceptions(t);

			// If no persephone exception has been found => try from Exception#getCause
			if(!exceptionHandled) {
				exceptionHandled = handlePersephoneExceptions(t.getCause());
			}

			if(exceptionHandled) {
				return;
			} else if(t instanceof RuntimeException) {
				MensajeErrores mensaje = new MensajeErrores();
				mensaje.setClase("RuntimeException");
				mensaje.setMensaje(t.getMessage());;
				mensaje.setNumeroError(99999);
				
				
				displayErrorNotif("Unhandled runtime exception.", mensaje);
				return;
			} else {
				LOGGER.error("Persephone Error Handler: {} ; Message: {}", t.getClass(), t.getMessage());
			}
		}
	}

	private boolean handlePersephoneExceptions(Throwable t) {
		boolean handled = false;
		
		LOGGER.error("Por el metodo handekdeOresExceoto");

		// Technical runtime exceptions
		// Expected exceptions (not handled)
		 if(t instanceof ApplicationException) {
			ApplicationException e = (ApplicationException) t;
			displayErrorNotif("Error en Aplicacion " + e.getMessage(), e.getApplication());
			handled = true;
		}

		return handled;
	}

	private void displayErrorNotif(String msg, MensajeErrores t) {
		   LOGGER.error(String.format("Error handler: %s", msg), t);

		   if(!StringUtils.isEmpty(t.getMensaje())) {
		           new Notification(
		                   msg,
		                   t.getMensaje(),
		                   Notification.Type.ERROR_MESSAGE,
		                   false)
		                   .show(Page.getCurrent());
		       }
		}
}
