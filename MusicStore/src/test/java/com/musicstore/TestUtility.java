package com.musicstore;

import com.musicstore.entity.*;
import com.musicstore.request.CartRequest;
import com.musicstore.request.UserRequest;
import com.musicstore.response.UserResponse;
import com.musicstore.service.OrderServiceTest;
import com.musicstore.service.ProducerServiceTest;
import com.musicstore.service.ProductServiceTest;
import com.musicstore.service.ShipmentServiceTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtility {

  protected static final String DELETE_ID = "delete-id";
  protected static final String NAME = "name";
  protected static final String SURNAME = "surname";
  protected static final String MAIL = "mail";
  protected static final String PHONE_NUMBER = "phone-number";

  protected static final String CUSTOMER_ID = "customer-id";
  protected static final String PAYMENT_CARD = "payment-card";
  protected static final String BILLING_ADDRESS = "billing-address";

  protected static final int QUANTITY = 1;
  protected static final Timestamp DATE = Timestamp.from(Instant.now());
  protected static final Double PRICE = 1.0;
  protected static final Double OVERALL_PRICE = 2.0;

  protected static final Integer ID = 1;
  protected static final Integer PARENT = 1;
  protected static final String IMG_URL = "img_url1";
  protected static final String PRODUCER = "producer";

  protected static final double TOTAL = 1.0;
  protected static final String ADDRESS = "address";

  protected static final Integer PRODUCT_ID = 1;
  protected static final String PRODUCT_NAME = "product-name";
  protected static final Double PRODUCT_PRICE = 0.0;
  protected static final Integer LEFT_QUANTITY = 1;
  protected static final Integer SOLD_QUANTITY = 2;
  protected static final Integer CATEGORY_ID = 0;

  protected static final int SHIPMENT_ID = 0;
  protected static final Timestamp SHIP_DATE = Timestamp.from(Instant.now());
  protected static final Timestamp ARRIVE_DATE =
      Timestamp.from(Instant.now().plus(14, ChronoUnit.DAYS));
  protected static final String SHIP_ADDRESS = "ship-address";

  protected static final String PASSWORD = "password";

  protected static final UserRequest FIRST_ADMIN_USER_REQUEST =
      UserRequest.builder().mail("mail1@test").password("password1").build();

  protected static final UserRequest SECOND_ADMIN_USER_REQUEST =
      UserRequest.builder().mail("mail2@test").password("password2").build();

  protected static final Integer START_LIST = 0;
  protected static final Integer END_LIST = 2;

  protected static final String SECOND_USER_MAIL = "usermail2@test";

  protected static final String FIRST_USER_ID = "usermail1@test";
  protected static final String FIRST_USER_PASSWORD = "password1";
  protected static final String SECOND_USER_IMG_URL = "img_url2";
  protected static final String SECOND_USER_PASSWORD = "password2";
  protected static final String THIRD_USER_ID = "usermail3@test";

  protected static final String CATEGORY_NAME = "category_1";
  protected static final String NEW_NAME = "new_name";
  protected static final String PRODUCER_ID = "producer_1";

  protected static final String FIRST_ADMIN_ID = "mail1@test";
  protected static final String USER_ID = "usermail1@test";
  protected static final String USER_PASSWORD = "password1";

  protected static final String CREATE_ORDER_MAIL = "mail3@test";
  protected static final String CREATE_ORDER_ADDRESS = "address_3";

  protected static final String FIRST_PRODUCER_MAIL = "producermail1@test";
  protected static final String FIRST_PRODUCER_NAME = "producer_name1";
  protected static final String SECOND_PRODUCER_MAIL = "producermail2@test";
  protected static final String THIRD_PRODUCER_MAIL = "producermail3@test";
  protected static final String THIRD_PRODUCER_NAME = "producer_name3";
  protected static final String THIRD_PRODUCER_ADDRESS = "producer_address3";

  protected static final String FIRST_PRODUCT_NAME = "product_1";

  protected static void assertReasonException(
      ResponseStatusException actualException, HttpStatus httpStatus, String reasonsConstant) {
    ResponseStatusException expectedException =
        new ResponseStatusException(httpStatus, reasonsConstant);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }

  protected Optional<Admin> buildOptionalAdmin() {
    Admin admin = buildAdmin();
    return Optional.of(admin);
  }

  protected static Admin buildAdmin() {
    return Admin.builder().mail(MAIL).name(NAME).surname(SURNAME).phoneNumber(PHONE_NUMBER).build();
  }

  protected static Customer buildCustomer() {
    return Customer.builder()
        .mail(CUSTOMER_ID)
        .name(NAME)
        .surname(SURNAME)
        .paymentCard(PAYMENT_CARD)
        .billingAddress(BILLING_ADDRESS)
        .build();
  }

  protected static List<Customer> buildCustomerList() {
    List<Customer> customerList = new ArrayList<>();
    customerList.add(buildCustomer());
    customerList.add(buildCustomer());
    return customerList;
  }

  protected CartRequest buildCartRequest() {
    return CartRequest.builder()
        .productId(ProductServiceTest.PRODUCT_ID)
        .quantity(QUANTITY)
        .mail(SECOND_USER_MAIL)
        .build();
  }

  protected Product buildProduct(Double price) {
    return Product.builder().id(ProductServiceTest.PRODUCT_ID).price(price).build();
  }

  protected static Cart buildCart(Integer i) {
    return Cart.builder()
        .id(i)
        .productId(PRODUCT_ID + i)
        .quantity(QUANTITY)
        .mail(SECOND_USER_MAIL)
        .date(DATE)
        .overallPrice(QUANTITY * TOTAL)
        .build();
  }

  protected static Category buildCategory() {
    return Category.builder().id(ID).name(CATEGORY_NAME).parent(PARENT).imgUrl(IMG_URL).build();
  }

  protected static void assertCategory(Category expectedCategory) {
    assertEquals(expectedCategory.getId(), ID);
    assertEquals(expectedCategory.getName(), CATEGORY_NAME);
    assertEquals(expectedCategory.getParent(), PARENT);
    assertEquals(expectedCategory.getImgUrl(), IMG_URL);
  }

  protected static Order buildOrder() {
    return Order.builder().id(ID).mail(MAIL).date(DATE).total(TOTAL).address(ADDRESS).build();
  }

  protected static Producer buildProducer() {
    return Producer.builder().mail(MAIL).name(NAME).address(ADDRESS).build();
  }

  protected static Product buildProduct() {
    return Product.builder()
        .id(PRODUCT_ID)
        .name(PRODUCT_NAME)
        .price(PRODUCT_PRICE)
        .leftQuantity(LEFT_QUANTITY)
        .soldQuantity(SOLD_QUANTITY)
        .producer(PRODUCER)
        .category(CATEGORY_ID)
        .imgUrl(IMG_URL)
        .build();
  }

  protected static Shipment buildShipment() {
    return Shipment.builder()
        .shipDate(SHIP_DATE)
        .arriveDate(ARRIVE_DATE)
        .shipAddress(SHIP_ADDRESS)
        .total(TOTAL)
        .orderId(OrderServiceTest.ID)
        .build();
  }

  protected static User buildUser() {
    return User.builder().mail(MAIL).password(PASSWORD).imgUrl(IMG_URL).build();
  }

  protected static UserRequest buildUserRequest() {
    return UserRequest.builder().mail(MAIL).password(PASSWORD).build();
  }

  protected static UserResponse buildUserResponse() {
    return UserResponse.builder().mail(MAIL).imgUrl(IMG_URL).build();
  }

  protected static List<Cart> buildCartList() {
    List<Cart> cartList = new ArrayList<>();
    for (Integer i = START_LIST; i < END_LIST; ++i) {
      cartList.add(buildCart(i));
    }
    return cartList;
  }

  protected void assertCart(Cart actualCart, Integer id) {
    assertEquals(id, actualCart.getId());
    assertEquals(PRODUCT_ID + id, actualCart.getProductId());
    assertEquals(QUANTITY, actualCart.getQuantity());
    assertEquals(SECOND_USER_MAIL, actualCart.getMail());
    assertEquals(DATE, actualCart.getDate());
  }

  protected List<Order> buildOrderList() {
    List<Order> orderList = new ArrayList<>();
    orderList.add(OrderServiceTest.buildOrder());
    orderList.add(OrderServiceTest.buildOrder());
    return orderList;
  }

  protected List<Producer> buildProducerList() {
    List<Producer> producerList = new ArrayList<>();
    producerList.add(ProducerServiceTest.buildProducer());
    return producerList;
  }

  protected List<Shipment> buildShipmentList() {
    List<Shipment> shipmentList = new ArrayList<>();
    shipmentList.add(ShipmentServiceTest.buildShipment());
    shipmentList.add(ShipmentServiceTest.buildShipment());
    return shipmentList;
  }

  protected List<User> buildUserList() {
    List<User> userList = new ArrayList<>();
    userList.add(buildUser());
    userList.add(buildUser());
    return userList;
  }

  protected List<UserResponse> buildUserResponseList() {
    List<UserResponse> userResponseList = new ArrayList<>();
    userResponseList.add(buildUserResponse());
    userResponseList.add(buildUserResponse());
    return userResponseList;
  }

  protected static User genericBuildUser(String userId, String userPassword) {
    return User.builder().mail(userId).password(userPassword).build();
  }

  protected static UserRequest genericBuildUserRequest(String userId, String userPassword) {
    return UserRequest.builder().mail(userId).password(userPassword).build();
  }

  protected List<CartRequest> buildCartRequestList() {
    List<CartRequest> cartRequestList = new ArrayList<>();
    cartRequestList.add(buildCartRequest());
    cartRequestList.add(buildCartRequest());
    return cartRequestList;
  }

  protected UserRequest buildAdminUserRequest() {
    return UserRequest.builder().mail(FIRST_ADMIN_ID).password(USER_PASSWORD).build();
  }

  protected UserRequest buildAuthenticUserRequest() {
    return UserRequest.builder().mail(USER_ID).password(USER_PASSWORD).build();
  }

  protected UserRequest buildAuthProducerUserRequest() {
    return UserRequest.builder().mail(FIRST_PRODUCER_MAIL).password(USER_PASSWORD).build();
  }

  protected Producer buildThirdProducer() {
    return Producer.builder()
        .mail(THIRD_PRODUCER_MAIL)
        .name(THIRD_PRODUCER_NAME)
        .address(THIRD_PRODUCER_ADDRESS)
        .build();
  }
}
