package ba.academy.diary;
import ba.academy.diary.dto.DiaryInput;
import ba.academy.diary.services.DiaryService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("/diary")
public class DiaryResource {

  @Inject DiaryService diaryService;




  @GET
  public Response getDiary()
  {
    List<DiaryInput> allDiaryInputs = diaryService.getDiary();
    if(allDiaryInputs == null || allDiaryInputs.isEmpty()) {
      return Response.noContent().build();
    }
    return  Response.ok(allDiaryInputs).build();
  }

  @GET
  @Path("{id}")
  @Consumes({MediaType.APPLICATION_JSON})
  public Response getDiaryById(@PathParam("id") int id, @Context UriInfo uriInfo)
  {
    DiaryInput diary = diaryService.getDiaryById(id);
    if(diary != null) {
      UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
      uriBuilder.path(String.valueOf(id));
      return Response.ok(uriBuilder.build()).entity(diary).build();
    }
    return Response.noContent().build();
  }

  @DELETE   // DELETE - by id
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteDiaryById(@PathParam("id") int id, @Context UriInfo uriInfo)
  {
    if(diaryService.deleteDiaryById(id)) {
      UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
      uriBuilder.path(String.valueOf(id));
      return Response.ok().build();
    }
    return Response.noContent().build();
  }
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

  @PUT  // PUT - update by id
  @Path("{id}")
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response editDiary(@PathParam("id") int id,DiaryInput diaryInput, @Context UriInfo uriInfo)
  {
    DiaryInput storedDiary = diaryService.editDiary(id,diaryInput);
    if(storedDiary != null) {
      UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
      uriBuilder.path(Integer.toString(storedDiary.getId()));
      return Response.created(uriBuilder.build()).status(Response.Status.OK).entity(storedDiary).build();
    }
    return Response.status(Response.Status.BAD_REQUEST).build();
  }
}
