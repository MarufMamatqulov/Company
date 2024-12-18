package com.example.Company.Cotroller;

import com.example.Company.Entity.Client;
import com.example.Company.Entity.Employee;
import com.example.Company.Service.ClientService;
import com.example.Company.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public Client registerClient(@RequestBody Client client, @RequestParam Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return clientService.registerClient(client, employee);
    }

    @GetMapping("/list/{employeeId}")
    public List<Client> listClient(@PathVariable Long employeeId) {
        return clientService.getClientByEmployee(employeeId);
    }

    @PutMapping("/archive/{clientId}")
    public String ArchiveClient(@PathVariable Long clientId) {
        clientService.ArchiveClient(clientId);
        return "Client Archived";
    }

    @GetMapping("/{clientId}")
    public Client getClientDetails (@PathVariable Long clientId) {
        return clientService.getClientDetails(clientId);
    }

    @PutMapping("/update/{clientId}")
    public Client updateClient(@PathVariable Long clientId, @RequestBody Client updatedClient) {
        return clientService.updateClient(clientId, updatedClient);
    }


    @GetMapping("/archived")
    public List<Client> getArchivedClients() {
        return clientService.getArchivedClients();
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "Client Deleted";
    }

    // 1. Kunlik ro'yxatdan o'tgan mijozlar soni
    @GetMapping("/daily-registrations")
    public ResponseEntity<List<Object[]>> getDailyRegisteredClients() {
        return ResponseEntity.ok(clientService.getDailyRegisteredClients());
    }

    // 2. Eng ko'p mijoz ro'yxatdan o'tkazgan xodim
    @GetMapping("/top-registrar")
    public ResponseEntity<Object[]> getTopRegistrarEmployee() {
        return ResponseEntity.ok(clientService.getTopRegistrarEmployee());
    }

    // 3. Eng ko'p mijoz ro'yxatdan o'tkazgan top 3 xodim
    @GetMapping("/top-3-registrars")
    public ResponseEntity<List<Object[]>> getTop3RegistrarEmployees() {
        return ResponseEntity.ok(clientService.getTop3RegistrarEmployees());
    }

    // 4. So'nggi 1 oyda ro'yxatdan o'tgan mijozlar soni
    @GetMapping("/last-month-registrations")
    public ResponseEntity<Long> getClientsRegisteredLastMonth() {
        return ResponseEntity.ok(clientService.getClientsRegisteredLastMonth());
    }


    // 5. So'nggi 1 oyning eng faol kuni
    @GetMapping("/most-active-day-last-month")
    public ResponseEntity<Object[]> getMostActiveDayLastMonth() {
        return ResponseEntity.ok(clientService.getMostActiveDayLastMonth());
    }

}
