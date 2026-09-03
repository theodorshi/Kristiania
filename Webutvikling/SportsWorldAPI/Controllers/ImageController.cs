using Microsoft.AspNetCore.Mvc;

namespace SportsWorldAPI.Controllers;

[ApiController]
[Route("api/[controller]")]

public class ImageController(IWebHostEnvironment _webHostEnvironment) : ControllerBase
{
    [HttpPost]
    public async Task<IActionResult> Post(IFormFile file)
    {
        try
        {
            string webRootPath = _webHostEnvironment.WebRootPath;
            string absolutePath = Path.Combine(webRootPath, "images", file.FileName);

            using (var fileStream = new FileStream(absolutePath, FileMode.Create))
            {
                await file.CopyToAsync(fileStream);
            }
            return Created();
        }
        catch
        {
            return StatusCode(500);
        }
    }
}