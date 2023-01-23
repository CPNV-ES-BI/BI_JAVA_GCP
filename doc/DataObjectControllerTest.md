classDiagram
direction BT
class DataObject {
<<Interface>>
  + delete(String) void
  + create(String, String) void
  + list() void
  + create(String, String, String) void
  + isExist(String) boolean
  + deleteRecursively(String) void
  + publish(String) URI
  + download(String, String) boolean
  + isExist(String, String) boolean
}
class DataObjectController {
  + DataObjectController() 
  + String BUCKET_NAME
  - Storage storage
  + create(String, String, String) void
  + delete(String) void
  + isExist(String) boolean
  + isExist(String, String) boolean
  + list() void
  + deleteRecursively(String) void
  + download(String, String) boolean
  + publish(String) URI
  + create(String, String) void
}
class DataObjectControllerTest {
  + DataObjectControllerTest() 
  - String fileName
  - DataObjectController dataObjectController
  - String fileName2
  - String destination
  - String content
  + test_DeleteObject_ObjectExists_ObjectDeleted() void
  + test_DownloadObject_NominalCase_Success() void
  + test_PublishObject_ObjectNotFound_ThrowException() void
  ~ setUpBeforeClass() void
  + test_CreateObject_PathNotExists_Success() void
  + test_DownloadObject_NotExists_ThrowException() void
  + test_DoesExist_NotExists_False() void
  + test_DoesExist_ExistsCase_True() void
  + test_CreateObject_NominalCase_ObjectExists() void
  ~ tearDownAfterClass() void
  ~ tearDown() void
  + test_DeleteObject_ObjectContainingSubObjectsExists_ObjectDeletedRecursively() void
  ~ setUp() void
  + test_DeleteObject_ObjectDoesntExist_ThrowException() void
  + test_PublishObject_NominalCase_Success() void
  + test_CreateObject_AlreadyExists_ThrowException() void
}
class ObjectAlreadyExistsException {
  + ObjectAlreadyExistsException(String) 
}
class ObjectNotExistsException {
  + ObjectNotExistsException(String) 
}

DataObject  ..>  ObjectAlreadyExistsException 
DataObject  ..>  ObjectNotExistsException 
DataObjectController  ..>  DataObject 
DataObjectController  ..>  DataObjectController 
DataObjectController  ..>  ObjectAlreadyExistsException : «create»
DataObjectController  ..>  ObjectNotExistsException : «create»
DataObjectControllerTest "1" *--> "dataObjectController 1" DataObjectController 
DataObjectControllerTest  ..>  DataObjectController : «create»
DataObjectControllerTest  ..>  ObjectAlreadyExistsException 
DataObjectControllerTest  ..>  ObjectNotExistsException 
