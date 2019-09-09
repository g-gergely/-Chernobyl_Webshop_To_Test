const addBtn = document.getElementsByClassName('indexAddBtn');
const itemCounter = document.getElementById("basketItemCounter");
const basket = document.getElementById("basket");
const filterBasket = document.getElementById("filterBasket");
const search = document.getElementById("search");
const realSubmit = document.getElementById("real_submit");


for (let addButton of addBtn) {
    addButton.addEventListener("click", function () {
        let id = this.id.toString() + ",";

        let previous = parseInt(itemCounter.textContent, 10);
        ++previous;
        itemCounter.textContent = previous.toString();
        let dataItems = basket.getAttribute("href");
        dataItems += id;

        basket.setAttribute("href", dataItems);
        //filterBasket.setAttribute("value", id);
    })
}

search.addEventListener("click", function(){
        let basketItems = basket.getAttribute("href");
        basketItems = basketItems.substring(14);
        console.log("basket items: "+basketItems);
        filterBasket.setAttribute("value", basketItems);
        console.log(filterBasket.getAttribute("value"));

        realSubmit.click();


});











