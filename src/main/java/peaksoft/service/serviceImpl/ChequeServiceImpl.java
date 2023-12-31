package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.RestaurantRequestOfDay;
import peaksoft.dto.request.WaiterRequest;
import peaksoft.dto.response.*;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositry.*;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final MenuItemRepository menuItemRepository;
    private final StopListRepository stopListRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;


    public Restaurant getRestaurant() {
        Restaurant restaurant1 = null;
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            restaurant1 = restaurant;
        }
        return restaurant1;
    }

    @Override
    public ChequeResponse save(ChequeRequest request) {
        User user = userRepository.findById(request.getWaiterId())
                .orElseThrow(() ->
                        new NotFoundException(String.format("Author with email :%s already exists", request.getWaiterId())));
        if (user.getRole().equals(Role.WAITER)) {
            Cheque cheque = new Cheque();
            List<MenuItemResponse> menuItemResponses = new ArrayList<>();
            List<MenuItem> menuItems = new ArrayList<>();
            for (String s : request.getMenuItemNames()) {
                MenuItem itemName = menuItemRepository.findByName(s);
                MenuItemResponse byNameResponse = menuItemRepository.findByNameResponse(s);
                menuItemResponses.add(byNameResponse);
                menuItems.add(itemName);
            }
            cheque.setUser(user);
            cheque.setMenuItems(menuItems);
            cheque.setCreatedAt(LocalDate.now());
            int num = 0;
            int count = 0;
            for (MenuItemResponse menuItem : menuItemResponses) {
                System.out.println(menuItem.getPrice()+"bnm,.");
                num = num + menuItem.getPrice();
                count++;
                System.out.println(count);
            }

            int totalPrice = num + ((num / 100) * getRestaurant().getService());
            cheque.setPriceAverage(num / count);
            cheque.setTotal(num);
            cheque.setGrandTotal(totalPrice);
            chequeRepository.save(cheque);
            return new ChequeResponse(
                    cheque.getId(),
                    cheque.getUser().getFirstName().concat(" ").concat(cheque.getUser().getLastName()),
                    menuItemResponses,
                    num,
                    getRestaurant().getService(),
                    totalPrice,
                    cheque.getCreatedAt()
            );
        }
        throw  new BadRequestException("user id "+request.getWaiterId()+ " no waiter");
    }


    public List<ChequeResponse> cheques  (List<Cheque> cheques){
        List<ChequeResponse> chequeResponses = new ArrayList<>();
        for (Cheque cheque : cheques) {
            List<MenuItemResponse> menuItemResponses = new ArrayList<>();
            for (MenuItem menuItem : cheque.getMenuItems()) {
                menuItemResponses.add(new MenuItemResponse(
                        menuItem.getId(),
                        menuItem.getName(),
                        menuItem.getImage(),
                        menuItem.getPrice(),
                        menuItem.getDescription(),
                        menuItem.getIsVegetarian()
                ));
            }
            int num = 0;
            int count = 0;
            for (MenuItemResponse menuItem1 : menuItemResponses) {
                num = num + menuItem1.getPrice();
                count++;
            }
            chequeResponses.add(new ChequeResponse(
                    cheque.getId(),
                    "sdfghj",
                    menuItemResponses,
                    (num / count),
                    getRestaurant().getService(),
                    num,
                    cheque.getCreatedAt()
            ));

        }
        return chequeResponses;
    }

    @Override
    public WaiterResponseOfDay totalPriceWalter(WaiterRequest request) {
        User user = userRepository.findById(request.getWaiterId())
                .orElseThrow(() ->
                        new NotFoundException(String.format("User with email :%s already exists", request.getWaiterId())));
        List<Cheque> chequeList = chequeRepository.findAll().stream().filter(cheque -> Objects.equals(cheque.getUser().getId(), request.getWaiterId())).toList();
        List<Cheque> cheques = chequeList.stream().filter(cheque -> cheque.getCreatedAt().equals(request.getDay())).toList();
        List<ChequeResponse> cheques1 = cheques(cheques);
        WaiterResponseOfDay waiterResponseOfDay = new WaiterResponseOfDay();
        waiterResponseOfDay.setFirstName(user.getFirstName());
        waiterResponseOfDay.setLastName(user.getLastName());
        waiterResponseOfDay.setId(user.getId());
        waiterResponseOfDay.setChequeResponses(cheques1);
        int number = 0;
        for (ChequeResponse c : cheques1) {
            number += c.getGrandTotal();
        }
        waiterResponseOfDay.setAllDayChequePrice(number);
        return waiterResponseOfDay;
    }

    @Override
    public ChequeResponse update(Long id, ChequeRequest request) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Cheque with id :%s already exists", id)));
        User user = userRepository.findById(request.getWaiterId()).orElseThrow(() ->
                new NotFoundException(String.format("Cheque with id :%s already exists", id)));
        List<MenuItemResponse> menuItemResponses = new ArrayList<>();
        List<MenuItem> menuItemList = new ArrayList<>();
        for (String s : request.getMenuItemNames()) {
            MenuItem byName = menuItemRepository.findByName(s);
            MenuItemResponse byNameResponse = menuItemRepository.findByNameResponse(s);
            menuItemResponses.add(byNameResponse);
            cheque.setMenuItems(List.of(byName));
        }
        cheque.setUser(user);
        cheque.setCreatedAt(LocalDate.now());
        int num = 0;
        int count = 0;
        for (MenuItemResponse menuItem : menuItemResponses) {
            num = num + menuItem.getPrice();
            count++;
        }
        cheque.setPriceAverage(num / count);
        cheque.setMenuItems(menuItemList);
        int number = num / 100;
        int totalPrice = num + (number * getRestaurant().getService());
        cheque.setTotal(num);
        cheque.setGrandTotal(totalPrice);
        chequeRepository.save(cheque);
        return new ChequeResponse(
                cheque.getId(),
                cheque.getUser().getFirstName().concat(" ").concat(cheque.getUser().getLastName()),
                menuItemResponses,
                num,
                getRestaurant().getService(),
                totalPrice,
                cheque.getCreatedAt()
        );

    }


    @Override
    public List<ChequeResponse> getAll() {
        return cheques(chequeRepository.findAll());
    }

    @Override
    public RestaurantResponseOfDay totalPriceRestaurant(RestaurantRequestOfDay request) {
        List<Cheque> cheques = chequeRepository.findAll().stream().filter(cheque -> cheque.getCreatedAt().equals(request.getDay())).toList();
        List<ChequeResponse> cheques1 = cheques(cheques);
        RestaurantResponseOfDay restaurantResponseOfDay = new RestaurantResponseOfDay();
        restaurantResponseOfDay.setChequeResponses(cheques1);
        restaurantResponseOfDay.setName(getRestaurant().getName());
        restaurantResponseOfDay.setId(getRestaurant().getId());
        int number = 0;
        for (ChequeResponse c : cheques1) {
            number += c.getGrandTotal();
        }
        restaurantResponseOfDay.setAllDayChequePrice(number);
        return restaurantResponseOfDay;
    }


    @Override
    public SimpleResponse delete(Long id) {
        if (!stopListRepository.existsById(id)) {
            throw new NotFoundException("Cheque with id : " + id + "is not found");
        }
        stopListRepository.deleteById(id);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message(String.format("Cheque with id : %s is deleted!", id))
                .build();
    }

    @Override
    public ChequeResponse getById(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow();
        List<MenuItemResponse>menuItemResponseList = new ArrayList<>();
        for (MenuItem menuItem : cheque.getMenuItems()) {
            menuItemResponseList.add(
                    new MenuItemResponse(
                            menuItem.getId(),
                            menuItem.getName(),
                            menuItem.getImage(),
                            menuItem.getPrice(),
                            menuItem.getDescription(),
                            menuItem.getIsVegetarian()
                    )
            );
        }
        return new ChequeResponse(
                cheque.getId(),
                cheque.getUser().getFirstName().concat(" ").concat(cheque.getUser().getLastName()),
                menuItemResponseList,
                cheque.getTotal(),
                getRestaurant().getService(),
                cheque.getGrandTotal(),
                cheque.getCreatedAt()
        );
    }
}