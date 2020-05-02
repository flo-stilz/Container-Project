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

public class ShowAllListener extends ContainerController implements ActionListener{
	private boolean b;
	public ShowAllListener(Application app, ContainerSelectionPanels csp, boolean b, TopMain topmain) {
		super(app, csp, topmain);
		this.b = b;
	}

	public void actionPerformed(ActionEvent e) {
		ArrayList<Journey> journeys = new ArrayList<Journey>();
		if (b) {
			journeys = getApp().getJourneyContainerDat().getPastJourneys();
		}
		else {
			journeys = getApp().getJourneyContainerDat().getActiveJourneys();	
		}
		getCsp().setShowAllCommand(true);
		getCsp().setPast(b);
		ArrayList<Journey> result = filterJourneysForClients(getApp(), getTopmain(), journeys);
		if (getTopmain() instanceof ClientMain) {
			getCsp().setwContainers(getApp().getJourneyContainerDat().getAllContainers(true, result));
		}
		else {
			getCsp().setwContainers(getApp().getJourneyContainerDat().getAllContainers(true, result));
		}
		checksSearchEntryC(getApp(), getTopmain());
	
	}
	
	public void checksSearchEntryC(final Application application, final TopMain topmain) {
		if (getCsp().getwContainers().size() == 0) {
			if (getCsp().isShowAllCommand()) {
				new ErrorFrame();
			}
			else {
				new ErrorFrame(getCsp().getKeyword());
			}
		}
		else {
			getCsp().displayContainers();
			topmain.getCl().show(topmain.getCards(), "viewContainers");
		}
	}
	
	public ArrayList<Journey> filterJourneysForClients(final Application application, final TopMain topmain, ArrayList<Journey> unfiltered) {
		if (topmain instanceof ClientMain) {
			ArrayList<Journey> result = new ArrayList<Journey>();
			result.addAll(application.findClientJourneys(unfiltered));
			return result;
		}
		else {
			return unfiltered;
		}
	}
	
}