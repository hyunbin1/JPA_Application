package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Orders;
import jpabook.jpashop.domain.item.Items;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderSearch;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;


    // 상품 등록
    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findAllMember();
        List<Items> items = itemService.findAllItem();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String reorder(@RequestParam("memberId") Long memberId,
                          @RequestParam("itemId") Long itemId,
                          @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);
        return "redirect:/order";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Orders> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "/order/orderList";
    }
}
