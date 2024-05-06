package sit707_week7;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;

public class BodyTemperatureMonitorTest {

	@Test
	public void testStudentIdentity() {
		String studentId = "s224107868";
		Assert.assertNotNull("Student ID is empty", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Rajani Ishika";
		Assert.assertNotNull("Student name is empty", studentName);
	}
	
	@Test
	public void testReadTemperatureNegative() {
		TemperatureSensor temperatureSensor = () -> -1.0; 
        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(temperatureSensor, null, null);
        double temperature = btm.readTemperature();
        Assert.assertTrue("The temperature ought to be below zero", temperature < 0);
	}
	
	@Test
	public void testReadTemperatureZero() {
		 TemperatureSensor temperatureSensor = () -> 0.0; 
	        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(temperatureSensor, null, null);
	        double temperature = btm.readTemperature();
	        Assert.assertEquals("The temperature should be at freezing point", 0.0, temperature, 0.0);
	}
	
	@Test
	public void testReadTemperatureNormal() {
		TemperatureSensor temperatureSensor = () -> 37.0; 
        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(temperatureSensor, null, null);
        double temperature = btm.readTemperature();
        Assert.assertEquals("The temperature should be within the typical range", 37.0, temperature, 0.0);
	}

	@Test
	public void testReadTemperatureAbnormallyHigh() {
		TemperatureSensor temperatureSensor = () -> 40.0; 
        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(temperatureSensor, null, null);
        double temperature = btm.readTemperature();
        Assert.assertEquals("The temperature should be unusually elevated", 40.0, temperature, 0.0);
	}


	@Test
	public void testReportTemperatureReadingToCloud() {
		
		CloudService cloudService = mock(CloudService.class);
        TemperatureReading temperatureReading = new TemperatureReading(); 
        
        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(null, cloudService, null);
        btm.reportTemperatureReadingToCloud(temperatureReading);

        verify(cloudService).sendTemperatureToCloud(temperatureReading);
	}
	
	
	@Test
	public void testInquireBodyStatusNormalNotification() {
		CloudService cloudService = mock(CloudService.class);
        NotificationSender notificationSender = mock(NotificationSender.class);

        when(cloudService.queryCustomerBodyStatus(any())).thenReturn("NORMAL");

        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(null, cloudService, notificationSender);
        btm.inquireBodyStatus();

	}
	
	
	@Test
	public void testInquireBodyStatusAbnormalNotification() {
		CloudService cloudService = mock(CloudService.class);
        NotificationSender notificationSender = mock(NotificationSender.class);

        when(cloudService.queryCustomerBodyStatus(any())).thenReturn("ABNORMAL");

        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(null, cloudService, notificationSender);
        btm.inquireBodyStatus();

	}
}