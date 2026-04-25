# Seasonal Adjustment of U.S. Real GDP

This note gives you a notebook-ready solution for the quarterly FRED series `NA000334Q`.

## 1. Libraries

```python
import pandas as pd
import matplotlib.pyplot as plt

from statsmodels.tsa.seasonal import seasonal_decompose, STL
```

If needed, install the dependencies first:

```python
pip install pandas matplotlib statsmodels
```

## 2. Download the data from FRED

```python
url = "https://fred.stlouisfed.org/graph/fredgraph.csv?id=NA000334Q"

gdp = pd.read_csv(url)
gdp["DATE"] = pd.to_datetime(gdp["DATE"])
gdp["NA000334Q"] = pd.to_numeric(gdp["NA000334Q"], errors="coerce")

gdp = gdp.dropna().set_index("DATE")
gdp = gdp.asfreq("QS")
gdp.columns = ["real_gdp"]

gdp.head()
```

## 3. Plot the original series

```python
plt.figure(figsize=(12, 5))
plt.plot(gdp.index, gdp["real_gdp"], color="black", label="Original series")
plt.title("U.S. Real GDP (NA000334Q)")
plt.xlabel("Date")
plt.ylabel("GDP")
plt.legend()
plt.grid(alpha=0.3)
plt.show()
```

## 4. Classical decomposition

For macroeconomic aggregates such as GDP, an additive decomposition is usually easier to interpret:

```python
classical = seasonal_decompose(
    gdp["real_gdp"],
    model="additive",
    period=4
)

classical.plot()
plt.suptitle("Classical Additive Decomposition", y=1.02)
plt.show()
```

### Seasonally adjusted series: classical method

In the additive case:

`Seasonally adjusted = Original series - Seasonal component`

```python
gdp["sa_classical"] = gdp["real_gdp"] - classical.seasonal
gdp[["real_gdp", "sa_classical"]].head()
```

## 5. STL decomposition

```python
stl = STL(gdp["real_gdp"], period=4, robust=True)
stl_result = stl.fit()

fig = stl_result.plot()
fig.set_size_inches(12, 8)
plt.suptitle("STL Decomposition", y=1.02)
plt.show()
```

### Seasonally adjusted series: STL method

Again, for an additive decomposition:

`Seasonally adjusted = Original series - Seasonal component`

```python
gdp["sa_stl"] = gdp["real_gdp"] - stl_result.seasonal
gdp[["real_gdp", "sa_stl"]].head()
```

## 6. Compare both seasonally adjusted series

```python
plt.figure(figsize=(12, 6))
plt.plot(gdp.index, gdp["real_gdp"], label="Original", color="gray", alpha=0.6)
plt.plot(gdp.index, gdp["sa_classical"], label="Seasonally adjusted - Classical", linewidth=2)
plt.plot(gdp.index, gdp["sa_stl"], label="Seasonally adjusted - STL", linewidth=2)
plt.title("Comparison of Seasonal Adjustment Methods")
plt.xlabel("Date")
plt.ylabel("GDP")
plt.legend()
plt.grid(alpha=0.3)
plt.show()
```

### Difference between both adjusted series

```python
gdp["difference"] = gdp["sa_classical"] - gdp["sa_stl"]

plt.figure(figsize=(12, 4))
plt.plot(gdp.index, gdp["difference"], color="darkred")
plt.axhline(0, color="black", linestyle="--", linewidth=1)
plt.title("Difference: Classical adjusted - STL adjusted")
plt.xlabel("Date")
plt.ylabel("Difference")
plt.grid(alpha=0.3)
plt.show()
```

## 7. Short discussion for the write-up

You can adapt the following text for your report:

### Interpretation

1. The classical decomposition separates the GDP series into trend, seasonal, and irregular components using moving averages.
2. The STL method also decomposes the series into trend, seasonal, and remainder, but it uses LOESS smoothing, which is more flexible.
3. The seasonally adjusted series from both methods are obtained by subtracting the seasonal component from the original GDP series.

### Comparison of results

1. The classical method imposes a more rigid seasonal structure, so the seasonal component tends to be smoother and less adaptive over time.
2. STL allows the seasonal pattern and the trend to evolve more flexibly, which is especially useful when the series has structural changes or unusual shocks.
3. For GDP, both seasonally adjusted series are usually close, but STL often handles turning points and abnormal periods better.
4. The differences between the two adjusted series are typically small in normal periods and can become more visible around recessions or exceptional events.

### Conclusion

The classical decomposition is simple and easy to explain, but STL is generally more robust and flexible. Therefore, STL is often preferable when the objective is to obtain a seasonally adjusted series that better adapts to changes in the data.

## 8. Optional: compact version for submission

```python
import pandas as pd
import matplotlib.pyplot as plt
from statsmodels.tsa.seasonal import seasonal_decompose, STL

url = "https://fred.stlouisfed.org/graph/fredgraph.csv?id=NA000334Q"
gdp = pd.read_csv(url)
gdp["DATE"] = pd.to_datetime(gdp["DATE"])
gdp["NA000334Q"] = pd.to_numeric(gdp["NA000334Q"], errors="coerce")
gdp = gdp.dropna().set_index("DATE").asfreq("QS")
gdp.columns = ["real_gdp"]

classical = seasonal_decompose(gdp["real_gdp"], model="additive", period=4)
gdp["sa_classical"] = gdp["real_gdp"] - classical.seasonal

stl_result = STL(gdp["real_gdp"], period=4, robust=True).fit()
gdp["sa_stl"] = gdp["real_gdp"] - stl_result.seasonal

plt.figure(figsize=(12, 6))
plt.plot(gdp.index, gdp["real_gdp"], label="Original", alpha=0.5)
plt.plot(gdp.index, gdp["sa_classical"], label="Adjusted - Classical")
plt.plot(gdp.index, gdp["sa_stl"], label="Adjusted - STL")
plt.legend()
plt.grid(alpha=0.3)
plt.show()
```
