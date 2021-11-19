package com.example.petstore;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {
	
	public List<Pet> pets=new ArrayList<>();
	public int initId=0;

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {
		List<Pet> responseArray = new ArrayList<Pet>();
		if(!this.pets.isEmpty()) {
			for(int i=0;i<this.pets.size();i++) {
				Pet temp=new Pet();
				temp.setPetId(pets.get(i).getPetId());
				temp.setPetAge(pets.get(i).getPetAge());
				temp.setPetName(pets.get(i).getPetName());
				temp.setPetType(pets.get(i).getPetType());
				
				responseArray.add(temp);
			}
		}
		
		return Response.ok(responseArray).build();
		
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Pet pet = new Pet();
		pet.setPetId(petId);
		pet.setPetAge(3);
		pet.setPetName("Buula");
		pet.setPetType("Dog");

		return Response.ok(pet).build();
		
	}
	
	@APIResponses(value= {
			@APIResponse(responseCode="201", description = "A new pet added", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet")))})
	@POST
	@Path("/newPet")
	public Response addPet(Pet petData) {
		petData.setPetId(initId+1);
		petData.setPetAge(petData.getPetAge());
		petData.setPetName(petData.getPetName());
		petData.setPetType(petData.getPetType());
		pets.add(petData);
		this.initId++;
		return Response.ok(petData).build();
		
	}
	
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet found", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "Pet doesnt exist for this id")})
	@GET
	@Path("/getPet/{petId}")
	public Response getPetByID(@PathParam("petId") int petId) {
		int index=-1;
		if(petId<0) {
			return Response.status(Status.NOT_FOUND).build();
		}else {
			for(int i=0;i<pets.size();i++) {
				if(pets.get(i).getPetId()==petId) {
					index=i;
				}
			}
		}
		Pet response=new Pet();
		if(index>0) {
			response.setPetName(pets.get(index).getPetName());
			response.setPetId(pets.get(index).getPetId());
			response.setPetType(pets.get(index).getPetType());
			response.setPetAge(pets.get(index).getPetAge());
		}else {
			return Response.status(404).build();
		}
		return Response.ok(response).build();
	}
	
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet Updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "Pet doesnt exist for this id")})
	@PUT
	@Path("/updatePet/{petId}")
	public Response updatePet(@PathParam("petId") int petId, Pet pet) {
		int index=-1;
		if(petId<0) {
			return Response.status(Status.NOT_FOUND).build();
		}else {
			for(int i=0;i<pets.size();i++) {
				if(pets.get(i).getPetId()==petId) {
					index=i;
				}
			}
		}
		
		Pet response=new Pet();
		if(index>0) {
			response.setPetName(pets.get(index).getPetName());
			response.setPetType(pets.get(index).getPetType());
			response.setPetAge(pets.get(index).getPetAge());
		}else {
			return Response.status(404).build();
		}
		return Response.ok(response).build();	
	}
	
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet Deleted", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "Pet doesnt exist for this id")})
	@DELETE
	@Path("/deletePet/{petId}")
	public Response deletePet(@PathParam("petId") int petId) {
		int index=-1;
		if(petId<0) {
			return Response.status(Status.NOT_FOUND).build();
		}else {
			for(int i=0;i<pets.size();i++) {
				if(pets.get(i).getPetId()==petId) {
					index=i;
				}
			}
		}
		
		if(index>0) {
			pets.remove(index);
		}else {
			return Response.status(404).build();
		}
		return Response.status(200).build();
	}
	
	//search pet by name
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet found by name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "Pet doesnt exist for this id")})
	@GET
	@Path("searchByName/{petName}")
	public Response getPetByID(@PathParam("petName") String petName) {
		int index=-1;
		if(petName=="") {
			return Response.status(Status.NOT_FOUND).build();
		}else {
			for(int i=0;i<pets.size();i++) {
				if(pets.get(i).getPetName()==petName) {
					index=i;
				}
			}
		}
		Pet response=new Pet();
		if(index>0) {
			response.setPetName(pets.get(index).getPetName());
			response.setPetId(pets.get(index).getPetId());
			response.setPetType(pets.get(index).getPetType());
			response.setPetAge(pets.get(index).getPetAge());
		}else {
			return Response.status(404).build();
		}
		return Response.ok(response).build();
	}
	
	
}
