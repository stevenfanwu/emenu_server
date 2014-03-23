thrift --gen java Base.thrift
thrift --gen java MenuService.thrift
thrift --gen java OrderService.thrift
thrift --gen java PadInfoService.thrift
thrift --gen java ProfileService.thrift
thrift --gen java WaiterService.thrift
ant deploy
