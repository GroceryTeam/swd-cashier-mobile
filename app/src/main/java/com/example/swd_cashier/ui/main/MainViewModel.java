package com.example.swd_cashier.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swd_cashier.models.BillRequest;
import com.example.swd_cashier.models.CartItem;
import com.example.swd_cashier.models.Product;
import com.example.swd_cashier.repositories.CartRepo;
import com.example.swd_cashier.repositories.ProductRepo;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    ProductRepo productRepo = new ProductRepo();
    CartRepo cartRepo = new CartRepo();
    MutableLiveData<Product> mutableProduct = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Product>> loadProducts(String searchTerm) {
        return productRepo.callGetProductsAPI(searchTerm);
    }

    public void setProduct(Product product) {
        mutableProduct.setValue(product);
    }

    public LiveData<List<CartItem>> getCart() {
        return cartRepo.getCart();
    }

    public boolean addItemToCart(Product product) {
        return cartRepo.addItemToCart(product);
    }

    public void checkoutBill(BillRequest billRequest) {
        cartRepo.checkoutBill(billRequest);
    }

    public void removeItemFromCart(CartItem cartItem) {
        cartRepo.removeItemFromCart(cartItem);
    }

    public void changeQuantity(CartItem cartItem, int quantity) {
        cartRepo.changeQuantity(cartItem, quantity);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public void resetCart() {
        cartRepo.initCart();
    }
}