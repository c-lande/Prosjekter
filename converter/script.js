const currency_elem_one = document.getElementById('currency-one');
const currency_elem_two = document.getElementById('currency-two');
const amount_elem_one = document.getElementById('amount-1');
const amount_elem_two = document.getElementById('amount-2');

const rate_elem1 = document.getElementById('rate');
const swap = document.getElementById('swap');

function calculate(){
    const currency_one = currency_elem_one.value;
    const currency_two = currency_elem_two.value;
    fetch(`https://v6.exchangerate-api.com/v6/b2599531e763a8437ea43a0f/latest/${currency_one}`)
    .then(res => res.json())
    .then((data)=>{
        const rate = data.conversion_rates[currency_two];
        rate_elem1.innerText = `1 ${currency_one} = ${rate} ${currency_two}`;

        amount_elem_two.value = (amount_elem_one.value * rate).toFixed(2);
    });
}

currency_elem_one.addEventListener('change', calculate);
currency_elem_two.addEventListener('change', calculate);
amount_elem_one.addEventListener('input', calculate);
amount_elem_two.addEventListener('input', calculate);

swap.addEventListener('click', () => {
    const temp = currency_elem_two.value;
    currency_elem_two.value = currency_elem_one.value;
    currency_elem_one.value = temp;
    calculate();
})

calculate();