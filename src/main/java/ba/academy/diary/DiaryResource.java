package ba.academy.diary;

import ba.academy.diary.dto.DiaryInput;
import ba.academy.diary.services.DiaryService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("/diary")
public class DiaryResource {

  @Inject DiaryService diaryService;


  // PUT - update by id

  @GET
  public Response getDiary()
  {
    List<DiaryInput> allDiaryInputs = diaryService.getDiary();
    if(allDiaryInputs == null || allDiaryInputs.isEmpty()) {
      return Response.noContent().build();
    }
    return  Response.ok(allDiaryInputs).build();
  }
  // GET - get by id
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDiaryById(@QueryParam("id") int id, @Context UriInfo uriInfo)
  {
    DiaryInput diary = diaryService.getDiaryById(id);
    if(diary != null) {
      UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
      uriBuilder.path(String.valueOf(id));
      return Response.ok(uriBuilder.build()).entity(diary).build();
    }
    return Response.noContent().build();
  }
  // DELETE - by id

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces(value = MediaType.APPLICATION_JSON)

  public Response createDiary(DiaryInput diaryInput, @Context UriInfo uriInfo)
  {
    DiaryInput storedDiary = diaryService.addDiaryInput(diaryInput);
    if(storedDiary != null) {
      UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
      uriBuilder.path(Integer.toString(storedDiary.getId()));
      return Response.created(uriBuilder.build()).entity(storedDiary).build();
    }
    return Response.status(Response.Status.BAD_REQUEST).build();
  }

}
