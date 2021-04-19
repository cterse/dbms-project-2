package edu.ncsu.projects.dbms2.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ncsu.projects.dbms2.dao.StoreDao;
import edu.ncsu.projects.dbms2.entity.Store;
import edu.ncsu.projects.dbms2.entity.StoreInventory;

@Component
public class StoreMenu {
	private final List<String> menuList = new ArrayList<>();
	private static Scanner scan = new Scanner(System.in);
	
	@Autowired
	private StoreDao storeDao;

	public StoreMenu() {
		menuList.add("View all Store details");
		menuList.add("View Store inventory");
		menuList.add("Add new Store");
		menuList.add("Update Store by ID");
		menuList.add("Delete Store by ID");
		menuList.add("Inactivate Store by ID");
		menuList.add("Return product to warehouse");
		menuList.add("Transfer between stores");
		menuList.add("Back to Main Menu");
	}

	private void executeAction(int choice) {
		switch(choice) {
		case 1: 
			getAllStores();
			break;
		case 2:
			getStoreInventory();
			break;
		case 3:
			addStore();
			break;
		case 4:
			updateStore();
			break;
		case 5:
			deleteStore();
			break;
		case 6:
			inactivateStore();
			break;
		case 7:
			returnToWH();
			break;
		case 8:
			transferToStore();
			break;
		default:
			System.out.println("Invalid Choice");
		}
	}
	
	private void deleteStore() {
		System.out.println("Enter store ID to delete: ");
		Integer storeId = scan.nextInt();
		
		int deletedStore = storeDao.deleteStore(storeId);
		
		System.out.println("Deleted "+ deletedStore +" rows.");
	}

	private void updateStore() {
		System.out.println("Enter store ID to update: ");
		Integer storeId = scan.nextInt();
		
		System.out.println("Enter attribute name: ");
		String attributeName = scan.next().toUpperCase();
		
		scan.nextLine();
		System.out.println("Enter attribute value: ");
		String attributeValue = scan.nextLine();
		
	
		int updateCount = storeDao.updateByAttribute(attributeName, attributeValue, storeId);
		
		System.out.println("Updated "+ updateCount +" rows in STORES table.");
	}

	private void addStore() {
		Store store = new Store();
		
		scan.nextLine();
		System.out.println("Enter address: ");
		store.setAddress(scan.nextLine());
		
		scan.nextLine();
		System.out.println("Enter phone: ");
		store.setPhone(scan.nextLine());
		
		System.out.println("Enter active status (true/false): ");
		store.setActiveStatus(scan.nextBoolean());
		
		scan.nextLine();
		System.out.println("Enter managerid: ");
		store.setManagerId(scan.nextInt());
		
		int count = storeDao.addStore(store);
		System.out.println("Added "+ count +" rows.");
	}

	private void getStoreInventory() {
		System.out.print("Enter Store id: ");
		Integer storeId = scan.nextInt();
		
//		scan.nextLine();
//		System.out.println("Enter attribute value: ");
		StoreInventory store = storeDao.viewStoreInv(storeId);
		
		System.out.println(store);
	}

	private void getAllStores() {
		// List<Store> stores = storeService.getAllStores();
		List<Store> stores = storeDao.findAll();
		
		for(Store store : stores) {
			System.out.println(store);
		}
	}
	

	private void returnToWH() {
		System.out.print("Enter Store id for return: ");
		Integer storeId = scan.nextInt();
		
		scan.nextLine();
		System.out.print("Enter Product id for return: ");
		Integer productId = scan.nextInt();
		
		scan.nextLine();
		System.out.print("Enter quantity for return: ");
		Integer quantity = scan.nextInt();
		
		List<StoreInventory> stores = storeDao.returnStoreToWarehouse(storeId, productId, quantity);
		System.out.println(stores);
//		TODO
	}
	
	private void inactivateStore() {
		System.out.println("Enter store ID to be set as inactive: ");
		Integer storeId = scan.nextInt();
		
		int deletedStore = storeDao.removeFromDb(storeId);
		
		System.out.println("Set "+ deletedStore +" rows to inactive.");
	
	}
	
	private void transferToStore() {
//		TODO
		System.out.println("Enter from store ID: ");
		Integer fromStoreId = scan.nextInt();
		
		scan.next();
		System.out.println("Enter to store ID: ");
		Integer toStoreId = scan.nextInt();
		
		scan.next();
		System.out.println("Enter product ID: ");
		Integer productID = scan.nextInt();
		
		scan.next();
		System.out.println("Enter to quantity: ");
		Integer quantity = scan.nextInt();
		
		List<StoreInventory> inventoryList = storeDao.storeToStore(fromStoreId, toStoreId, quantity, productID);
		System.out.println(inventoryList);
	
		
	}
	
	public void loadMenu() {
		while (true) {
			printStoreMenu();
			
			System.out.print("Enter choice: ");
			int choice = scan.nextInt();
			
			if (choice == menuList.size()) {
				break;
			}
			
			try {
				executeAction(choice);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void printStoreMenu() {
		System.out.println("STORE ACTIONS:");
		for (int i=0; i<menuList.size(); i++) {
			System.out.println(i+1 +": "+ menuList.get(i));
		}
	}
}


