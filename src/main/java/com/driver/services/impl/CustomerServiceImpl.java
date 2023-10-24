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
import java.util.Comparator;
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
		Customer customer = customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception {
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList = driverRepository2.findAll();
		driverList.sort(Comparator.comparingInt(Driver::getDriverId));

		Driver driver = null;
		for (Driver d : driverList)
			if (d.getCab().getAvailable()) {
				driver = d;
				break;
			}

		if (driver == null)
			throw new Exception("No cab available!");

		Customer customer = customerRepository2.findById(customerId).get();

		int bill = driver.getCab().getPerKmRate() * distanceInKm;

		TripBooking bookedTrip = new TripBooking(fromLocation, toLocation, distanceInKm, TripStatus.CONFIRMED, bill, driver, customer);

		driver.getCab().setAvailable(false);
		driver.getTripBookingList().add(bookedTrip);
		driverRepository2.save(driver);

		customer.getTripBookingList().add(bookedTrip);
		customerRepository2.save(customer);

		tripBookingRepository2.save(bookedTrip);

		return bookedTrip;
	}

	@Override
	public void cancelTrip(Integer tripId) {
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking trip = tripBookingRepository2.findById(tripId).get();
		trip.setStatus(TripStatus.CANCELED);
		trip.setBill(0);

		Driver driver = trip.getDriver();
		driver.getCab().setAvailable(true);

		driverRepository2.save(driver);
		tripBookingRepository2.save(trip);
	}

	@Override
	public void completeTrip(Integer tripId) {
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking trip = tripBookingRepository2.findById(tripId).get();
		trip.setStatus(TripStatus.COMPLETED);

		Driver driver = trip.getDriver();
		driver.getCab().setAvailable(true);

		driverRepository2.save(driver);
		tripBookingRepository2.save(trip);
	}
}
