# BlueskyControllerApi

All URIs are relative to *http://localhost:8081*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**addInstance1**](#addinstance1) | **POST** /bluesky/instances | |
|[**deleteInstance1**](#deleteinstance1) | **DELETE** /bluesky/instances/{id} | |
|[**getHomeTimeline1**](#gethometimeline1) | **POST** /bluesky/home | |
|[**getInstances1**](#getinstances1) | **GET** /bluesky/instances | |

# **addInstance1**
> addInstance1(addBlueskyInstanceRequest)


### Example

```typescript
import {
    BlueskyControllerApi,
    Configuration,
    AddBlueskyInstanceRequest
} from './api';

const configuration = new Configuration();
const apiInstance = new BlueskyControllerApi(configuration);

let addBlueskyInstanceRequest: AddBlueskyInstanceRequest; //

const { status, data } = await apiInstance.addInstance1(
    addBlueskyInstanceRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **addBlueskyInstanceRequest** | **AddBlueskyInstanceRequest**|  | |


### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **deleteInstance1**
> deleteInstance1()


### Example

```typescript
import {
    BlueskyControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new BlueskyControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.deleteInstance1(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


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

# **getHomeTimeline1**
> Array<Message> getHomeTimeline1()


### Example

```typescript
import {
    BlueskyControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new BlueskyControllerApi(configuration);

const { status, data } = await apiInstance.getHomeTimeline1();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<Message>**

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

# **getInstances1**
> Array<BlueskyService> getInstances1()


### Example

```typescript
import {
    BlueskyControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new BlueskyControllerApi(configuration);

const { status, data } = await apiInstance.getInstances1();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<BlueskyService>**

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

