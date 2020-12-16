package lt.akademijait.flightmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;



import lt.itakademija.exam.FlightManager;
import lt.itakademija.exam.Passenger;
import lt.itakademija.exam.Plane;
import lt.itakademija.exam.SeatIsOccupiedException;
import lt.itakademija.exam.test.BaseTest;

public class MyFlightManager extends BaseTest {

	public static void main(String[] args) {
		System.out.println();
		try {
			System.setErr(new PrintStream(new File("Error.log")));   /// nukreipiam i faila error.log
			System.err.println("Error message 1");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try	{
			throw new Exception("Error message...");
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	@Override
	protected FlightManager createFlightManager() {
		return new FlightManager() {
			private List<Passenger> passengers = new ArrayList<>();
			private List<Plane> planes = new ArrayList<>();

			@Override
			public Passenger createPassenger(String name, String surname, int age) {
				if(name == null || surname == null) {
					throw new NullPointerException();
				}
				Passenger newPassenger = new Passenger(name, surname, age);
				return newPassenger;
			}

			@Override
			public Plane createPlane(String id, int seats) {
		        if (id == null || seats == 0) {
		            throw new NullPointerException();
		        } else {
		            Plane newPlane = new Plane(id, seats);
		            planes.add(newPlane);
		            return newPlane;
		        }
			}

			@Override
			public double getAveragePassengerAge(String planeId) {
				int averageAge = 0;
				for (Plane plane : planes) {
				if (plane.getId().equals(planeId)) {
					int age = 0;
					for (int i = 0; i < plane.getPassengers().size(); i++) {
						age += plane.getPassengers().get(i).getAge();
					}
					if (plane.getPassengers().size() != 0) {
						averageAge = age / plane.getPassengers().size();						
					}
				}
			}
				return averageAge;
			}

			@Override
			public List<Plane> getCreatedPlanes() {
				return planes;
			}

			@Override
			public Passenger getOldestPassenger(String planeId) {
				Passenger passenger = null;
				if (planeId == null) {
					throw new NullPointerException();
				} else {
					for (Plane plane : planes) {
						if (plane.getId().equals(planeId)) {
							int oldest = plane.getPassengers().get(0).getAge();
							passenger = plane.getPassengers().stream().max(Comparator.comparing(Passenger::getAge)).get();				
						}					
					}					
				return passenger;
				}
			}
			
			@Override
			public List<Passenger> getPassengers(String planeId) {
				List<Passenger> passengersList = new ArrayList<>();
				if (planeId == null) {
					throw new NullPointerException();
				}else {
					for (Plane plane : planes) {
						if (plane.getId().equals(planeId)) {
							passengersList = plane.getPassengers();
						}
					}	
				return passengersList;
				}			
			}			
						
			@Override
			public Plane getPlaneById(String id) {
				if (id == null) {
					throw new NullPointerException();
				}
				Plane registeredPlane = null;
				for (Plane plane : planes) {
					if (plane.getId().equals(id)) {
						registeredPlane = plane;				
					}
				}
				return registeredPlane;
			}

			
			@Override
			public void registerPassenger(Plane plane, int seatNo, Passenger passenger) throws SeatIsOccupiedException {
				if(plane.isSeatOccupied(seatNo)) {
					throw new SeatIsOccupiedException();
				} else {
					passenger.setSeatNo(seatNo);
					plane.registerPassenger(seatNo, passenger);
				}
				
			}
		};
	}
}

	
	


