
const API_KEY = 'f3848c6af9a78756f76792a8d3a4dd57';

// 城市列表
const cities = [
    { name: "北京", code: "Beijing" },
    { name: "上海", code: "Shanghai" },
    { name: "广州", code: "Guangzhou" },
    { name: "深圳", code: "Shenzhen" },
    { name: "涿州", code: "Zhuozhou" }
];

// 初始化城市下拉框
function initCitySelector() {
    const citySelect = document.getElementById('citySelect');
    cities.forEach(city => {
        const option = document.createElement('option');
        option.value = city.code;
        option.textContent = city.name;
        citySelect.appendChild(option);
    });
    citySelect.value = cities[0].code;
    fetchWeather(cities[0].code); // 初始加载天气
}

// 获取天气信息
async function fetchWeather(cityCode) {
    const weatherContainer = document.getElementById('weatherContainer');
    weatherContainer.innerHTML = '<div class="col-12 text-center">正在加载天气...</div>';

    try {
        const res = await axios.get('https://api.openweathermap.org/data/2.5/forecast', {
            params: {
                q: cityCode,
                appid: API_KEY,
                units: 'metric',
                lang: 'zh_cn'
            }
        });

        const dailyWeather = res.data.list.filter((_, index) => index % 8 === 0).slice(0, 3);

        weatherContainer.innerHTML = '';
        dailyWeather.forEach(day => {
            const col = document.createElement('div');
            col.className = 'weather-day';

            col.innerHTML = `
        <img class="weather-icon" src="https://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png" alt="${day.weather[0].description}" />
        <p><strong>${new Date(day.dt_txt).toLocaleDateString()}</strong></p>
        <p>${day.weather[0].description}</p>
        <h5>${Math.round(day.main.temp)}°C</h5>
      `;
            weatherContainer.appendChild(col);
        });

    } catch (err) {
        console.error(err);
        weatherContainer.innerHTML = '<div class="col-12 text-danger text-center">天气获取失败</div>';
    }
}

// 城市选择变化
document.getElementById('citySelect').addEventListener('change', (e) => {
    fetchWeather(e.target.value);
});

// 页面初始化
initCitySelector();