# MastodonControllerApi

All URIs are relative to *http://localhost:8081*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**addInstance**](#addinstance) | **POST** /mastodon/instances | |
|[**deleteInstance**](#deleteinstance) | **DELETE** /mastodon/instances/{id} | |
|[**getHomeTimeline**](#gethometimeline) | **POST** /mastodon/home | |
|[**getInstances**](#getinstances) | **GET** /mastodon/instances | |
|[**getPublicTimeline**](#getpublictimeline) | **POST** /mastodon/pub | |

# **addInstance**
> addInstance(addMastodonInstanceRequest)


### Example

```typescript
import {
    MastodonControllerApi,
    Configuration,
    AddMastodonInstanceRequest
} from './api';

const configuration = new Configuration();
const apiInstance = new MastodonControllerApi(configuration);

let addMastodonInstanceRequest: AddMastodonInstanceRequest; //

const { status, data } = await apiInstance.addInstance(
    addMastodonInstanceRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **addMastodonInstanceRequest** | **AddMastodonInstanceRequest**|  | |


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

# **deleteInstance**
> deleteInstance()


### Example

```typescript
import {
    MastodonControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new MastodonControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.deleteInstance(
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

# **getHomeTimeline**
> Array<Message> getHomeTimeline()


### Example

```typescript
import {
    MastodonControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new MastodonControllerApi(configuration);

const { status, data } = await apiInstance.getHomeTimeline();
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

# **getInstances**
> Array<MastodonService> getInstances()


### Example

```typescript
import {
    MastodonControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new MastodonControllerApi(configuration);

const { status, data } = await apiInstance.getInstances();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<MastodonService>**

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

# **getPublicTimeline**
> Array<Message> getPublicTimeline()


### Example

```typescript
import {
    MastodonControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new MastodonControllerApi(configuration);

const { status, data } = await apiInstance.getPublicTimeline();
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

