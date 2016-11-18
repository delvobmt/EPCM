package com.ntk.epcm.manage.bean;

import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.Severity;
import com.ntk.epcm.service.DeviceNotificationService;
import com.ntk.epcm.service.DeviceService;

@Component
public class DashboardView implements InitializingBean, Observer{
	private DashboardModel model = new DefaultDashboardModel();
	private PieChartModel devicesModel = new PieChartModel();
	private PieChartModel notificationsModel = new PieChartModel();
	
	@Inject
	DeviceService deviceService;
	
	@Inject
	DeviceNotificationService deviceNoticationService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		DashboardColumn column1 = new DefaultDashboardColumn();
		DashboardColumn column2 = new DefaultDashboardColumn();
		
		column1.addWidget("devices");
		column2.addWidget("notifications");
		
		model.addColumn(column1);
		model.addColumn(column2);
		
		devicesModel.setLegendPosition("e");
		devicesModel.setShowDataLabels(true);
		devicesModel.setSeriesColors("ff5000, 80ff80"); // red and green

		notificationsModel.setLegendPosition("e");
		notificationsModel.setShowDataLabels(true);
		notificationsModel.setSeriesColors("ff5000, ffa500, 80ff80, 8080ff"); //red, orange, green and blue
		
		deviceService.addObserver(this);
		deviceNoticationService.addObserver(this);
		refresh();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		refresh();
	}
	
	public void refresh(){
		long countOnDevices = deviceService.countStatus(true);
		long countOffDevices = deviceService.countStatus(false);
		
		devicesModel.getData().put("OFF", countOffDevices);
		devicesModel.getData().put("ON", countOnDevices);
		
		long countError = deviceNoticationService.countSevertiy(Severity.ERROR.toString());
		long countWarn = deviceNoticationService.countSevertiy(Severity.WARN.toString());
		long countInfo = deviceNoticationService.countSevertiy(Severity.INFO.toString());
		long countNotification = deviceNoticationService.countSevertiy(Severity.NOTIFICATION.toString());
		
		notificationsModel.getData().put(Severity.ERROR.toString(), countError);
		notificationsModel.getData().put(Severity.WARN.toString(), countWarn);
		notificationsModel.getData().put(Severity.INFO.toString(), countInfo);
		notificationsModel.getData().put(Severity.NOTIFICATION.toString(), countNotification);
	}
	
	public DashboardModel getModel() {
		return model;
	}
	
	public PieChartModel getDevicesModel() {
		return devicesModel;
	}
	
	public PieChartModel getNotificationsModel() {
		return notificationsModel;
	}

	
}
