# SigninControllerApi

All URIs are relative to *http://localhost:8081*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**register**](#register) | **POST** /register | |

# **register**
> register()


### Example

```typescript
import {
    SigninControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new SigninControllerApi(configuration);

const { status, data } = await apiInstance.register();
```

### Parameters
This endpoint does not have any parameters.


### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

