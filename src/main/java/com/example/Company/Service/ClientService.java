package com.example.Company.Service;

import com.example.Company.Entity.Client;
import com.example.Company.Entity.Employee;
import com.example.Company.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client registerClient(Client client, Employee employee) {
        client.setCreatedAt(LocalDate.now());
        client.setCreatedBy(employee);
        return clientRepository.save(client);
    }

   public List<Client> getClientByEmployee(Long employeeId) {
        return clientRepository.findByCreatedByIdAndArchivedFalse(employeeId);
   }

    public String ArchiveClient(Long clientId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client with ID " + clientId + " not found"));

        // Agar mijoz allaqachon arxivlangan bo'lsa, xatolik qaytarish
        if (client.isArchived()) {
            throw new IllegalStateException("Client with ID " + clientId + " is already archived");
        }

        // Mijozni arxivlash
        client.setArchived(true);
        clientRepository.save(client);

        // Muvaffaqiyatli arxivlash haqida xabar
        return "Client with ID " + clientId + " successfully archived";
    }


    public Client getClientDetails(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
   }

    public Client updateClient(Long clientId, Client updatedClient) {
        // Mavjud mijozni clientId orqali topish
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client with ID " + clientId + " not found"));

        // Yangilanishni amalga oshirish
        if (updatedClient.getFirstName() != null) {
            existingClient.setFirstName(updatedClient.getFirstName());
        }
        if (updatedClient.getLastName() != null) {
            existingClient.setLastName(updatedClient.getLastName());
        }
        if (updatedClient.getPassportNumber() != null) {
            existingClient.setPassportNumber(updatedClient.getPassportNumber());
        }
        if (updatedClient.getPassportSeries() != null) {
            existingClient.setPassportSeries(updatedClient.getPassportSeries());
        }
        if (updatedClient.getJshshir() != null) {
            existingClient.setJshshir(updatedClient.getJshshir());
        }
        if (updatedClient.getAddress() != null) {
            existingClient.setAddress(updatedClient.getAddress());
        }

        // Mijozni saqlash
        return clientRepository.save(existingClient);
    }


    public List<Client> getArchivedClients() {
        return clientRepository.findByArchivedTrue();
    }

    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

}
