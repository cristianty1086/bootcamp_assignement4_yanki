package com.nttdata.bootcamp.assignement4.msyanki.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Document(value = "parameters")
//@RedisHash("parameters")
public class Parameter {
	
	@Id
    private String id;
	
	@NotNull
	private String parameter;
	
	@NotNull
	private String value;
	
}
