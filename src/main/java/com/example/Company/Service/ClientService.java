package com.example.Company.Service;

import com.example.Company.Entity.Client;
import com.example.Company.Entity.Employee;
import com.example.Company.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // 1. Kunlik ro'yxatdan o'tgan mijozlar
    public List<Object[]> getDailyRegisteredClients() {
        return clientRepository.findDailyRegisteredClients();
    }

    // 2. Eng ko'p mijoz ro'yxatdan o'tkazgan xodim
    public Object[] getTopRegistrarEmployee() {
        List<Object[]> results = clientRepository.findTopRegistrarEmployee();
        return results.isEmpty() ? null : results.get(0);
    }

    // 3. Eng ko'p mijoz ro'yxatdan o'tkazgan top 3 xodim
    public List<Object[]> getTop3RegistrarEmployees() {
        Pageable pageable = PageRequest.of(0, 3);
        return clientRepository.findTop3RegistrarEmployees(pageable);
    }

    // 4. So'nggi 1 oyda ro'yxatdan o'tgan mijozlar soni
    public Long getClientsRegisteredLastMonth() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);
        return clientRepository.countClientsRegisteredLastMonth(startDate, endDate);
    }

    // 5. So'nggi 1 oyning eng faol kuni
    public Object[] getMostActiveDayLastMonth() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);
        List<Object[]> results = clientRepository.findMostActiveDayLastMonth(startDate, endDate);
        return results.isEmpty() ? null : results.get(0);
    }

}
