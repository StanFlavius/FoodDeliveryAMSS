package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.delivery.AddDeliveryPersonRequestBodyDto;
import com.example.fooddelivery.exception.DeliveryPerson.IncorrectScheduleExp;
import com.example.fooddelivery.exception.userExp.EmailExist;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repositoryEM.DeliveryPersonRepositoryEM;
import com.example.fooddelivery.repositoryEM.RoleEntityRepositoryEM;
import com.example.fooddelivery.repositoryJpa.DeliveryPersonRepository;
import com.example.fooddelivery.repositoryJpa.RoleEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Autowired
    private NormalUserService normalUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleEntityRepository roleEntityRepository;

    @Autowired
    private RoleEntityRepositoryEM roleEntityRepositoryEM;

    @Autowired
    private DeliveryPersonRepositoryEM deliveryPersonRepositoryEM;

    public DeliveryPerson addDelivery(AddDeliveryPersonRequestBodyDto request) {
        if(request.getScheduleStart() > 20)
            throw new IncorrectScheduleExp("The delivery person can not start his job later than 20:00");

        if(request.getScheduleStart() < 6)
            throw new IncorrectScheduleExp("The delivery person can not start his job earlier than 6:00");

        if(request.getScheduleStop() > 0 && request.getScheduleStop() < 6)
            throw new IncorrectScheduleExp("The delivery person can work until 24:00");

        if((request.getScheduleStop() - request.getScheduleStart() < 4) ||
                (request.getScheduleStop() - request.getScheduleStart() > 8))
            throw new IncorrectScheduleExp("The delivery person should work at least 4 hours and at most 8 hours");

        UserEntity userEntity = normalUserService.findByLogin(request.getEmail());
        if (userEntity != null)
            throw new EmailExist("User with email: " + request.getEmail() + " already exists");

        userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode("123456"));
        userEntity.setEmail(request.getEmail());

        RoleEntity userRole = roleEntityRepositoryEM.findByName("ROLE_DELIVERY");
        userEntity.setRoleEntity(userRole);

        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setUserEntity(userEntity);
        deliveryPerson.setFirstName(request.getFirstName());
        deliveryPerson.setLastName(request.getLastName());
        deliveryPerson.setAvailability("AVAILABLE");
        deliveryPerson.setSalary(request.getSalary());
        deliveryPerson.setScheduleStart(request.getScheduleStart());
        deliveryPerson.setScheduleStop(request.getScheduleStop());
        deliveryPerson.setUserEntity(userEntity);

        deliveryPersonRepository.save(deliveryPerson);

        return deliveryPerson;
    }

    public DeliveryPerson editSalary(Integer id, Integer newSalary) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.getById(id);
        deliveryPerson.setSalary(newSalary);

        deliveryPersonRepository.save(deliveryPerson);

        return deliveryPerson;
    }

    public DeliveryPerson editSchedule(Integer id, Integer schStart, Integer schStop){
        DeliveryPerson deliveryPerson = deliveryPersonRepositoryEM.findByDeliveryPersonId(id);
        if(deliveryPerson == null)
            throw new IncorrectScheduleExp("Delivery person with id: " + id.toString() + " does not exist");

        if(schStart > 20)
            throw new IncorrectScheduleExp("The delivery person can not start his job later than 20:00");

        if(schStart < 6)
            throw new IncorrectScheduleExp("The delivery person can not start his job earlier than 6:00");

        if(schStop > 0 && schStop < 6)
            throw new IncorrectScheduleExp("The delivery person can work until 24:00");

        if((schStop - schStart < 4) ||
                (schStop - schStart > 8))
            throw new IncorrectScheduleExp("The delivery person should work at least 4 hours and at most 8 hours");

        deliveryPerson.setScheduleStart(schStart);
        deliveryPerson.setScheduleStop(schStop);
        deliveryPersonRepository.save(deliveryPerson);

        return deliveryPerson;
    }
//
//    public String deleteDeliveryPerson(Integer id) {
//        DeliveryPerson deliveryPerson = deliveryPersonRepository.findByDeliveryPersonId(id);
//        UserEntity userEntity = deliveryPerson.getUserEntity();
//        userService.deleteUser(userEntity.getId());
//        deliveryPersonRepository.delete(deliveryPersonRepository.getById(id));
//
//        return "Delivery person has been fired";
//    }
//
//    public DeliveryGuyDataDto getDataOneDeliveryPerson(Integer id){
//        DeliveryPerson deliveryPerson = deliveryPersonRepository.findByDeliveryPersonId(id);
//
//        DeliveryGuyDataDto finalDelivery = new DeliveryGuyDataDto();
//
//        finalDelivery.setFirstName(deliveryPerson.getFirstName());
//        finalDelivery.setLastName(deliveryPerson.getLastName());
//        finalDelivery.setOrdersNo(deliveryPerson.getOrderList().size());
//        finalDelivery.setScheduleStop(deliveryPerson.getScheduleStop());
//        finalDelivery.setSalary(deliveryPerson.getSalary());
//        finalDelivery.setScheduleStart(deliveryPerson.getScheduleStart());
//        finalDelivery.setEmploymentDate(deliveryPerson.getEmploymentDate());
//        finalDelivery.setLastDayOfSalaryRaise(deliveryPerson.getLastDayOfSalaryRaise());
//        finalDelivery.setSeniority(- ((int) DAYS.between(LocalDate.now(), deliveryPerson.getEmploymentDate())));
//        //finalDelivery.setSeniority(deliveryPerson.getSeniority());
//
//        List<OrderDto> orderDtoList = new ArrayList<>();
//        for (Order order : deliveryPerson.getOrderList()) {
//            List<OrderMenuAssoc> orders = new ArrayList<>();
//            orders = orderMenuAssocRepository.getOrderMenuAssocsByOrder(order);
//
//            List<MenuList> menus = new ArrayList<>();
//            for (OrderMenuAssoc orderMenu : orders)
//                menus.add(orderMenu.getMenu());
//
//            List<String> finalMenuList = new ArrayList<>();
//            for (MenuList m : menus) {
//                finalMenuList.add(m.getMenuName());
//            }
//
//            OrderDto finalOrder = new OrderDto();
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String orderTime = sdf.format(order.getOrderTime());
//            finalOrder.setOrderTime(orderTime);
//            finalOrder.setMenus(finalMenuList);
//            orderDtoList.add(finalOrder);
//        }
//        finalDelivery.setOrders(orderDtoList);
//
//        return finalDelivery;
//    }
//
//    public List<DeliveryGuyDataDto> getDataAllDeliveryPerson(){
//        List<DeliveryGuyDataDto> deliveryList = new ArrayList<>();
//        for (DeliveryPerson deliveryPerson : deliveryPersonRepository.findAll()) {
//            DeliveryGuyDataDto finalDelivery = getDataOneDeliveryPerson(deliveryPerson.getDeliveryPersonId());
//
//            deliveryList.add(finalDelivery);
//        }
//
//        return deliveryList;
//    }
//
//    public List<SalarySituation> checkSalarySituation() {
//        List<SalarySituation> salarySituationList = new ArrayList<>();
//        for (DeliveryPerson deliveryPerson : deliveryPersonRepository.findAll()) {
//            SalarySituation salarySituation = new SalarySituation();
//
//            salarySituation.setFirstName(deliveryPerson.getFirstName());
//            salarySituation.setLastName(deliveryPerson.getLastName());
//            salarySituation.setOldSalary(deliveryPerson.getSalary());
//            salarySituation.setEmploymentDate(deliveryPerson.getEmploymentDate());
//            salarySituation.setLastSalaryRaise(deliveryPerson.getLastDayOfSalaryRaise());
//
//            Integer seniority = - ((int) DAYS.between(LocalDate.now(), deliveryPerson.getEmploymentDate()));
//            salarySituation.setReason("The employee has a seniority of: " + seniority.toString() + " days. ");
//            if((seniority >= 30 && seniority < 60))
//                salarySituation.setReason(salarySituation.getReason() + "He/She deserves a 5% pay raise.");
//            else if((seniority >= 60 && seniority < 90))
//                salarySituation.setReason(salarySituation.getReason() + "He/She deserves a 10% pay raise.");
//            else if((seniority >= 90))
//                salarySituation.setReason(salarySituation.getReason() + "He/She deserves a 15% pay raise.");
//            else
//                salarySituation.setReason(salarySituation.getReason() + "He/She does not deserve a pay raise.");
//            salarySituationList.add(salarySituation);
//        }
//
//        return salarySituationList;
//    }
}
