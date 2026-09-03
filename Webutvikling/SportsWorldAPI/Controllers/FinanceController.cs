using Microsoft.AspNetCore.Mvc;
using SportsWorldAPI.Context;
using SportsWorldAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace SportsWorldAPI.Controllers;

[ApiController]
[Route("api/[controller]")]

public class FinanceController(SportsWorldContext _sportsWorldContext) : ControllerBase
{
    [HttpGet]
    public async Task<ActionResult<Finance>> Get()
    {
        try
        {
            Finance finance = await _sportsWorldContext.Finances.FirstAsync();
            if (finance == null)
            {
                return NotFound();
            }
            return Ok(finance);
        }
        catch
        {
            return StatusCode(500);
        }
    }

    [HttpPut]
   public async Task<IActionResult> Put(Finance editedFinance)
    {
        try
        {
            _sportsWorldContext.Entry(editedFinance).State = EntityState.Modified;
            await _sportsWorldContext.SaveChangesAsync();
            return Ok(200);
        }
        catch
        {
            return StatusCode(500);
        }
    }
}
