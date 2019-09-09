const quantities = document.getElementsByClassName("amounts");
const productNames = document.getElementsByClassName("productNames");
const productTotal = document.getElementsByClassName("productTotal");
const summField = document.getElementById("money");
const addButtons = document.getElementsByClassName("add");
const removeButtons = document.getElementsByClassName("remove");
const bins = document.getElementsByClassName("bin");
const productRows = document.getElementsByClassName("productRows");
const clearingCartBtn = document.getElementById("deleteAll");
const reviewTable = document.getElementById("review-table");
const checkoutBtn = document.getElementById("checkoutBtn");
const backToShoppingBtn = document.getElementById("review");
var currentQuantity;
var products = {};  //string name: int quantity
var productNamesAndDefaultPrice = {}; //string name: int, or float price

function checkout() {
    checkoutBtn.addEventListener("click", function () {
        if (parseFloat(summField.textContent) > 0.0) {
            directing("/checkout");
        }
        else{
            alert("Empty cart!");
        }
    })
}

function goBackShopping() {
    backToShoppingBtn.addEventListener("click", function(){
        directing("/");
    })
}



function directing(route) {
    let direct = document.createElement("a");
    let urlWithDatas = route + "?cartProducts=" + createRequestString();
    direct.setAttribute("href", urlWithDatas);
    summField.appendChild(direct);
    direct.click();
    direct.parentElement.removeChild(direct);
}

function clearCart() {
    clearingCartBtn.addEventListener("click", function () {
        if (parseInt(summField.textContent, 10) > 0) {
            summField.textContent = "0.0";
            reviewTable.parentElement.removeChild(reviewTable);
        }
    })

}

function deleteRow() {
    for (let bin of bins) {
        bin.addEventListener("click", function () {
            let row = this.parentElement.parentElement;
            let rubel = parseFloat(row.getElementsByClassName("productTotal")[0].textContent);
            let total = parseFloat(summField.textContent);
            row.parentElement.removeChild(row);
            summField.textContent = (total - rubel).toString();

        })
    }
}

function putNamesAndPrices() {
    for (let name of productNames) {
        productNamesAndDefaultPrice[name.textContent] = parseInt(name.parentElement.getElementsByClassName("productTotal")[0].textContent, 10) / products[name.textContent];
        console.log(name.textContent);
        console.log(productNamesAndDefaultPrice[name.textContent]);
        console.log(name.parentElement.getElementsByClassName("productTotal")[0].textContent);
        console.log(products[name.textContent]);
    }
}

function putProductsToDictonary() {
    for (let product of productNames) {
        products[product.textContent] = parseInt(product.nextElementSibling.firstElementChild.textContent, 10); // itt lehet majd returnolni kell
        console.log("this is what i put as value in products dictonary");
        console.log(product.nextElementSibling.firstElementChild.textContent);
    }
}


function atoTextandBack() {
    putProductsToDictonary();

    for (let quantity of quantities) {
        quantity.addEventListener("click", callbackForAddEventListener);
    }
}

function callbackForAddEventListener(event) {
    let amount = event.currentTarget;
    currentQuantity = amount.textContent;
    let x = document.createElement("INPUT");
    x.setAttribute("type", "text");
    x.setAttribute("placeholder", "Type here");
    x.setAttribute("type", "number");
    x.addEventListener("blur", createQuantityAnchor);
    amount.parentNode.replaceChild(x, amount);
}


function createQuantityAnchor(event) {
    let x = event.currentTarget;
    let parentTD = x.parentElement;
    let elm = $(x);                //that the way to get input value
    let inputText = elm.val();
    let q = document.createElement("a");
    q.setAttribute("tabindex", "0");
    q.setAttribute("class", "btn btn-lg btn-danger amounts");
    q.setAttribute("role", "button");
    q.setAttribute("data-toggle", "popover");
    q.setAttribute("data-trigger", "focus");
    q.setAttribute("title", "Click to type custom amount");
    q.setAttribute("data-content", "Click to type custom amount");
    x.parentNode.replaceChild(q, x);
    parentTD.firstElementChild.addEventListener("click", callbackForAddEventListener);
    onBlurAmount(q, inputText);
}

function onBlurAmount(q, text) {
    let key = q.parentElement.previousElementSibling.textContent;
    console.log(q);
    if (Number.isInteger(parseInt(text, 10)) && parseInt(text, 10) > 0 && !(text.includes(".")) || text.includes("3.6")) {
        console.log("this is number");
        q.textContent = text;
        currentQuantity = text;
        products[key] = parseInt(text, 10);
        setProductTotal(q);
    } else {
        q.textContent = currentQuantity;
        console.log("this was not a number")
    }
}

function setProductTotal(quantity) {
    console.log("first number: ");
    console.log(quantity.parentElement.parentElement.childNodes[5].firstElementChild.textContent);
    console.log("second number: ");
    console.log(productNamesAndDefaultPrice[quantity.parentElement.parentElement.firstElementChild.nextElementSibling.textContent]);
    quantity.parentElement.nextElementSibling.textContent = (productNamesAndDefaultPrice[quantity.parentElement.parentElement.firstElementChild.nextElementSibling.textContent] * parseInt(quantity.parentElement.parentElement.childNodes[5].firstElementChild.textContent, 10)).toString();
    setSum();
}

function setSum() {
    let counter = 0;
    for (let rowSumm of productTotal) {
        counter += parseInt(rowSumm.textContent, 10);
    }
    summField.textContent = counter.toString();
}

function addOrRemoveOne() {
    for (let addbtn of addButtons) {
        addbtn.addEventListener("click", addingOrRemoving);
    }
    for (let removebtn of removeButtons) {
        removebtn.addEventListener("click", addingOrRemoving);
    }

}

function addingOrRemoving(event) {
    let currentBtn = event.currentTarget;
    let currentRow = currentBtn.parentElement.parentElement.parentElement;
    let currentAmount = parseInt(currentRow.getElementsByClassName("amounts")[0].textContent, 10);
    console.log(currentBtn.className);
    let result = currentBtn.className.includes("add") ? currentAmount + 1 : checkDelete(currentAmount);
    currentRow.getElementsByClassName("amounts")[0].textContent = result.toString();
    setProductTotal(currentRow.getElementsByClassName("amounts")[0]);
}

function checkDelete(amount) {
    return amount > 1 ? amount - 1 : 1;

}

function createRequestString() {
    let resultString = "";
    if (!(parseFloat(summField.textContent) > 0)) return "";
    for (let row of productRows) {
        resultString += productString(row);
    }
    return resultString
}

function productString(row) {
    let key = row.getElementsByClassName("productNames")[0].textContent;
    let value = row.getElementsByClassName("amounts")[0].textContent;
    return key + ":" + value + ",";
}


atoTextandBack();
putNamesAndPrices();
addOrRemoveOne();
deleteRow();
console.log(createRequestString());
clearCart();
checkout();
goBackShopping();








