package market.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import market.model.entity.Order;
import market.model.repository.OrderRepository;
import market.service.DatabaseBackupService;
import org.postgresql.PGConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseBackupServiceImpl implements DatabaseBackupService {
    @Autowired
    private final OrderRepository orderRepository;
    @Override
    @Transactional
    public void createBackup() {
        String filePath = "data.json";
        List<Order> entities = orderRepository.findAllOrders();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(new File(filePath), entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
