# WelcomeControllerApi

All URIs are relative to *http://localhost:8081*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**welcome**](#welcome) | **GET** /index | |
|[**welcome1**](#welcome1) | **GET** / | |

# **welcome**
> string welcome()


### Example

```typescript
import {
    WelcomeControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new WelcomeControllerApi(configuration);

const { status, data } = await apiInstance.welcome();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **welcome1**
> string welcome1()


### Example

```typescript
import {
    WelcomeControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new WelcomeControllerApi(configuration);

const { status, data } = await apiInstance.welcome1();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

