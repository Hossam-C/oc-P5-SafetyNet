package com.SafetyNet.Alerts;

import com.SafetyNet.Alerts.DAO.LoadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.io.IOException;

@SpringBootApplication
public class AlertsApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AlertsApplication.class);
		//loadDataHooks(application);
		application.run(args);
		//SpringApplication.run(AlertsApplication.class, args);

	}

	static void loadDataHooks(SpringApplication application){
		application.addListeners((ApplicationListener<ApplicationStartedEvent>) event -> {


			LoadData initData = new LoadData();

			try {
				initData.loadData();
				initData.linkData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

}
