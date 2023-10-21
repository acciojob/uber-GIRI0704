package com.driver.services.impl;

import com.driver.controllers.CustomerController;
import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Optional<Customer> optionalCustomer = customerRepository2.findById(customerId);
		if(!optionalCustomer.isPresent()) return;
		Customer customer = optionalCustomer.get();
		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList = driverRepository2.findAll();
		int min = Integer.MAX_VALUE;
		Driver driver = null;

		for(Driver temp : driverList)
		{
			if(temp.getDriverId() < min && temp.getCab().isAvailable())
			{
				min = temp.getDriverId();
				driver = temp;
			}
		}
		 if(driver == null)
		 {
			 throw new Exception("No cab available!");
		 }
		driver.getCab().setAvailable(false);
		 TripBooking tripBooking = new TripBooking();
		 tripBooking.setFromLocation(fromLocation);
		 tripBooking.setToLocation(toLocation);
		 tripBooking.setDistanceInKm(distanceInKm);
		 tripBooking.setTripStatus(TripStatus.CONFIRMED);

		 Optional<Customer> optionalCustomer = customerRepository2.findById(customerId);
		 Customer customer = optionalCustomer.get();
		 customer.getTripBookingList().add(tripBooking);

		 driver.getTripBookingList().add(tripBooking);

		 tripBookingRepository2.save(tripBooking);
		 return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> optionalTripBooking = tripBookingRepository2.findById(tripId);
		if(!optionalTripBooking.isPresent()) return;
		TripBooking tripBooking = optionalTripBooking.get();

		tripBooking.setTripStatus(TripStatus.CANCELED);
		Driver driver = tripBooking.getDriver();

		Cab cab = driver.getCab();
		cab.setAvailable(true);

		tripBookingRepository2.save(tripBooking);
		driverRepository2.save(driver);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> optionalTripBooking = tripBookingRepository2.findById(tripId);
		if(!optionalTripBooking.isPresent()) return;
		TripBooking tripBooking = optionalTripBooking.get();

		tripBooking.setTripStatus(TripStatus.COMPLETED);
		Driver driver = tripBooking.getDriver();

		Cab cab = driver.getCab();
		cab.setAvailable(true);

		tripBookingRepository2.save(tripBooking);
		driverRepository2.save(driver);
	}
}
