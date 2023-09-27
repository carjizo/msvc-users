package org.sisvir.msvc.users.clients;

import org.sisvir.msvc.users.models.Device;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-devices", url = "localhost:8004", path = "/devices")
public interface DeviceClientRest {

    @GetMapping("/{id}")
    Device detailById(@PathVariable Long id);

    @PostMapping("/create")
    Device crear(@RequestBody Device device);

    @GetMapping("/findAllById")
    List<Device> findAllById(@RequestParam Iterable<Long> ids);

}
