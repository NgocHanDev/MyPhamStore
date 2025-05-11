const TOKEN_API = '20fbd60a-1752-11f0-95d0-0a92b8726859';
const PROVINCE_API = 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data';
const SHOP_ID = '196388';

window.onload = async () =>{
    const provinceTag = await document.getElementById('city');
    const provinces = await getProvinces();

    provinces.forEach( (province) => {
        if(province.ProvinceName === 'Test') {
            return ;
        }
        const option =  document.createElement('option');
        option.value = province.ProvinceID;
        option.textContent = province.ProvinceName;
         provinceTag.appendChild(option);
      });

      //get district when province change
    provinceTag.addEventListener('change', (e) => getDistricts(e.target.value));
}

const getProvinces = async () => {
    const response = await fetch(PROVINCE_API+'/province',{
        headers:{
            'Token': TOKEN_API,
            'Content-Type': 'application/json'
        },
        method: 'POST'
    });
    const provinces = await response.json();
    return provinces.data;
}
const getDistricts = async (provinceId) => {
        const districtTag = document.getElementById('district');
        const wardTag = document.getElementById('ward');
        districtTag.innerHTML = '<option value="" selected disabled>Chọn Quận/Huyện</option>';
        districtTag.disabled = true;
        wardTag.innerHTML = '<option value="" selected>Vui lòng chọn  Phường/xã</option>';
        wardTag.disabled = true;

        if (!provinceId) {
            return;
        }

        // Lấy danh sách quận/huyện
        const response = await fetch(`${PROVINCE_API}/district?province_id=${provinceId}`, {
            headers: {
                'Token': TOKEN_API,
                'Content-Type': 'application/json'
            },
            method: 'GET'
        });
        
        const districtsData = await response.json();
        const districts = districtsData.data;
        console.log(districtsData);
        
        // Kích hoạt quận/huyện và thêm tùy chọn
        districtTag.disabled = false;
        districts.forEach(district => {
            const option = document.createElement('option');
            option.value = district.DistrictID;
            option.textContent = district.DistrictName;
            districtTag.appendChild(option);
        });

        districtTag.addEventListener('change', (e) => getWards(e.target.value));
};

const getWards = async (districtId) => {

    const wardTag = document.getElementById('ward');
    wardTag.innerHTML = '<option value="" selected disabled>Chọn Phường/Xã</option>';
    wardTag.disabled = true;

    if (!districtId) {
        return;
    }

    // Lấy danh sách phường/xã
    const response = await fetch(`${PROVINCE_API}/ward?district_id=${districtId}`, {
        headers: {
            'Token': TOKEN_API,
            'Content-Type': 'application/json'
        },
        method: 'GET'
    });
    
    const wardsData = await response.json();
    const wards = wardsData.data;
    
    // Kích hoạt phường/xã và thêm tùy chọn
    wardTag.disabled = false;
    wards.forEach(ward => {
        const option = document.createElement('option');
        option.value = ward.WardCode;
        option.textContent = ward.WardName;
        wardTag.appendChild(option);
    });
    wardTag.addEventListener('change', (e)=>handleFeeShip(e.target.value, districtId))
}

const handleFeeShip = (wardId, districtId) =>{
    console.log("ward:", wardId, "district: ", districtId)

    const totalQuantityTag = document.getElementsByClassName('total-quantity');
    let totalQuantity = 0;

    for (const element of totalQuantityTag) {
        const value = Number.parseInt(element.textContent);
        if (!isNaN(value)) {
            totalQuantity += value;
        }
    }

    fetch('https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee', {
        headers: {
            'Token': TOKEN_API,
            'Content-Type': 'application/json',
            'ShopId': SHOP_ID
        },
        method: 'POST',
        body: JSON.stringify({
                "service_type_id":2,
                "to_district_id":Number.parseInt(districtId),
                "to_ward_code": `${wardId}`,
                "length":10,
                "width":20,
                "height":5,
                "weight":totalQuantity*100,
                "insurance_value":0,
                "coupon": null
            })
    })
        .then(data=>{
            return data.json()
        })
        .then(async (data) =>{
            const feeCost = await document.getElementById('fee-cost');
            const totalAmount = await document.getElementById('total-amount');
            const subTotalAmount = await document.getElementById('sub-total-amount');
            document.getElementById('submit-fee-cost').value = data.data.total

                feeCost.textContent = data.data.total + ' đ'
            const  totalAmountNum =  Number.parseInt(subTotalAmount.value);

            totalAmount.textContent = (totalAmountNum + data.data.total) + " đ"
        })
}




