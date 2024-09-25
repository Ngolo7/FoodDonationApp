package Implements;

import Interface.ConsumerInterface;
import Interface.DonorInterface;
import models.Consumer;
import models.Donor;

public class UserImplement implements DonorInterface, ConsumerInterface {
    @Override
    public void printConsumerData(Consumer consumer) {
        System.out.println("Consumer Data:");
        System.out.println("First Name: " + consumer.getFirstName());
        System.out.println("Last Name: " + consumer.getLastName());
        System.out.println("Email: " + consumer.getEmail());
        System.out.println("Role: " + consumer.getRole());
        System.out.println("-----------------------------------");
    }

    @Override
    public void printDonorData(Donor donor) {
        System.out.println("Donor Data:");
        System.out.println("First Name: " + donor.getFirstName());
        System.out.println("Last Name: " + donor.getLastName());
        System.out.println("Email: " + donor.getEmail());
        System.out.println("Role: " + donor.getRole());
        System.out.println("-----------------------------------");
    }
}
