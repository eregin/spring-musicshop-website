package com.example.accessingdatapostgresql.services;

import com.example.accessingdatapostgresql.entities.*;
import com.example.accessingdatapostgresql.repositories.EmployeeRepository;
import com.example.accessingdatapostgresql.repositories.SaleRepository;
import com.example.accessingdatapostgresql.repositories.ShopRepository;
import com.example.accessingdatapostgresql.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final SaleRepository saleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private ShopRepository shopRepository;

    public boolean createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null)
            return false;
        Customer customer = new Customer();
        List<Employee> employeeList = employeeRepository.findAllByTitle("Sales Support Agent");
        customer.setEmployee(employeeList.get( (int)(Math.random() * employeeList.size()) ));
        user.setCustomer(customer);
        user.getRoles().add(shopRepository.getAllRoles().stream().filter(p -> p.getName().equals("USER")).findAny().orElse(null));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        saleRepository.save(new Sale(LocalDateTime.now(), SaleStatus.CURRENT, this.getUserByName(user.getUsername()).getCustomer() ));
        return true;
    }
    public void userCustomer(User user) {
        user.setActive(true);
        userRepository.save(user);
    }
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Sale> getSales(Long id) {
        return saleRepository.findAllByCustomer_Id(id);
    }

    public Sale getCurrentSale(Long id) {
        return saleRepository.findSaleByCustomer_IdAndStatus(id, SaleStatus.CURRENT);
    }

    public void addToCart(Sale sale, Track track) {
        sale.getBuyTracks().add(track);
        saleRepository.save(sale);
    }
    public void delFromCart(Sale sale, Track track) {
        sale.getBuyTracks().remove(track);
        saleRepository.save(sale);
    }
    public void approveCart(Sale sale) {
        sale.setStatus(SaleStatus.APPROVED);
        saleRepository.save(sale);
        saleRepository.save(new Sale(LocalDateTime.now(), SaleStatus.CURRENT, sale.getCustomer() ));
    }
}
