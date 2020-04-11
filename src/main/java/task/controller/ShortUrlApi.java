package task.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import task.model.UrlDto;

import java.io.IOException;

@RequestMapping("/v1/url")
public interface ShortUrlApi {

    @ApiOperation(value = "Redirect by provided short URL")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found")})
    @GetMapping(value = "{key}")
    ResponseEntity<Object> redirectByUrl(@PathVariable String key) throws IOException;

    @ApiOperation(value = "Create short URL for provided link")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")})
    @PostMapping
    ResponseEntity<UrlDto> createUrl(@RequestBody UrlDto url);

    @ApiOperation(value = "Delete short URL")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found")})
    @DeleteMapping(value = "{key}")
    ResponseEntity<HttpStatus> deleteUrl(@PathVariable String key);
}
