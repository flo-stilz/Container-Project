package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.Application;
import model.Journey;
import view.ClientMain;
import view.ContainerSelectionPanels;
import view.ErrorFrame;
import view.TopMain;

public class ContainerController {
	private Application app;
	private ContainerSelectionPanels csp;
	private TopMain topmain;
	
	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public ContainerSelectionPanels getCsp() {
		return csp;
	}

	public void setCsp(ContainerSelectionPanels csp) {
		this.csp = csp;
	}

	public TopMain getTopmain() {
		return topmain;
	}

	public void setTopmain(TopMain topmain) {
		this.topmain = topmain;
	}

	public ContainerController(final Application app, ContainerSelectionPanels csp, TopMain topmain) {
		this.app = app;
		this.csp = csp;
		this.topmain = topmain;
		
		this.csp.addShowAllListener(csp.getShowAll(), new ShowAllListener(app, csp, false, topmain));
		
	}


}



