package com.merca.back.security.controller;

import com.merca.back.model.ImagenColor;
import com.merca.back.security.PayPalHttpClient;
import com.merca.back.security.dto.CapturePaypalOrderDTO;
import com.merca.back.security.dto.OrderDTO;
import com.merca.back.security.dto.OrderResponseDTO;
import com.merca.back.security.dto.PayPalAppContextDTO;
import com.merca.back.security.entity.Order;
import com.merca.back.security.enums.OrderStatus;
import com.merca.back.security.enums.PaymentLandingPage;
import com.merca.back.security.repository.OrderDAO;
import com.merca.back.service.ImagenColorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/checkout")
@CrossOrigin(origins = {"http://localhost:4200","https://mercalibre-365b2.web.app"})
@Slf4j
public class CheckoutController {
    private final PayPalHttpClient payPalHttpClient;
    private final OrderDAO orderDAO;
    private final RestTemplate restTemplate;

    @Autowired
    public CheckoutController(PayPalHttpClient payPalHttpClient, OrderDAO orderDAO, RestTemplate restTemplate) {
        this.orderDAO = orderDAO;
        this.payPalHttpClient = payPalHttpClient;
        this.restTemplate = restTemplate;
    }
    
    @Autowired
    ImagenColorService imgClrSrv;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> checkout(@RequestBody OrderDTO orderDTO) throws Exception {
        var appContext = new PayPalAppContextDTO();
//        appContext.setReturnUrl("http://localhost:8080/checkout/success");
        appContext.setBrandName("MercaLibre");
        appContext.setLandingPage(PaymentLandingPage.BILLING);
        orderDTO.setApplicationContext(appContext);
        var orderResponse = payPalHttpClient.createOrder(orderDTO);

        var entity = new Order();
        entity.setPaypalOrderId(orderResponse.getId());
        entity.setPaypalOrderStatus(orderResponse.getStatus().toString());
        var out = orderDAO.save(entity);
        log.info("Saved order: {}", out);
        return ResponseEntity.ok(orderResponse);
    }

//    @GetMapping(value = "/success")
//    public ResponseEntity<String> paymentSuccess(HttpServletRequest request) {
//        var orderId = request.getParameter("token");
//        var out = orderDAO.findByPaypalOrderId(orderId);
//        out.setPaypalOrderStatus(OrderStatus.APPROVED.toString());
//        orderDAO.save(out);
//        return ResponseEntity.ok().body("Payment success");
//    }
    
    @PostMapping(value = "/capture-paypal-order")
    public ResponseEntity<?> capturePaypalOrder(@RequestBody CapturePaypalOrderDTO capturePaypalOrderDTO) throws Exception {
        String orderID = capturePaypalOrderDTO.getOrderID();
        Object[] items = capturePaypalOrderDTO.getItems();
        for (int i = 0; i < items.length; i++) {
        Object item = items[i];
        if (item instanceof Map) {
            Map<String, Object> itemMap = (Map<String, Object>) item;
            // Acceder a las propiedades del objeto
            Object cantidad = itemMap.get("cantidad");
//            Object stock = itemMap.get("stock");
            Object ropaId = itemMap.get("id");
            // Realizar el procesamiento necesario con las propiedades obtenidas
            int cantidadNumerizada = Integer.parseInt(cantidad.toString());
//            int stockNumerizado = Integer.parseInt(stock.toString());
            int ropaIdNumerizado = Integer.parseInt(ropaId.toString());
            List<ImagenColor> imgClr = imgClrSrv.getImagenesColorByRopaId(ropaIdNumerizado);
            imgClr.get(0).setStock(imgClr.get(0).getStock() - cantidadNumerizada);
            imgClrSrv.save(imgClr.get(0));
        }
        }
        
        String paypalCaptureUrl = "https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderID + "/capture";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(payPalHttpClient.getAccessToken().getAccessToken());
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(paypalCaptureUrl, HttpMethod.POST, request, String.class);
//        var response = restTemplate.exchange(paypalCaptureUrl, HttpMethod.POST, request, String.class);
        System.out.println(response);
        
        // Actualizar el estado de la orden en tu base de datos
        var out = orderDAO.findByPaypalOrderId(orderID);
        out.setPaypalOrderStatus(OrderStatus.APPROVED.toString());
        orderDAO.save(out);
    
        // Devolver una respuesta exitosa
        return ResponseEntity.ok(response);
    }
}