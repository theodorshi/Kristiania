using Microsoft.AspNetCore.Mvc;
using SportsWorldAPI.Context;
using SportsWorldAPI.Models;
using Microsoft.EntityFrameworkCore;
using System.Runtime.CompilerServices;

namespace SportsWorldAPI.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AthleteController(SportsWorldContext _sportsWorldContext) : ControllerBase
{

    [HttpGet]
    public async Task<ActionResult<List<Athlete>>> Get()
    {
        try
        {
            List<Athlete> athletes = await _sportsWorldContext.Athletes.ToListAsync();
            return Ok(athletes);
        }
        catch
        {
            return StatusCode(500);
        }
    }
    
    [HttpGet("{id}")]
    public async Task<ActionResult<Athlete>> Get(int id)
    {
        try
        {
            Athlete? athlete = await _sportsWorldContext.Athletes.FindAsync(id);

            if (athlete != null)
            {
                return Ok(athlete);
            }
            else
            {
                return NotFound();
            }
        }
        catch
        {
            return StatusCode(500);
        }
    }


    [HttpPost]
    public async Task<ActionResult<Athlete>> Post(Athlete athlete)
    {
        try
        {
            if (athlete != null)
            {
                _sportsWorldContext.Athletes.Add(athlete);
                await _sportsWorldContext.SaveChangesAsync();

                return CreatedAtAction("Get", new {id = athlete.Id}, athlete);
            }
            else
            {
                return BadRequest();
            }
        }
        catch
        {
            return StatusCode(500);
        }
    }


    [HttpPut]
    public async Task<IActionResult> Put(Athlete edietAthlete)
    {
        try
        {
            _sportsWorldContext.Entry(edietAthlete).State = EntityState.Modified;
            await _sportsWorldContext.SaveChangesAsync();
            return Ok(200);
        }
        catch
        {
            return StatusCode(500);
        }
    }

     [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(int id)
    {
        try
        {
            Athlete? athlete = await  _sportsWorldContext.Athletes.FindAsync(id);
            if (athlete != null)
            {
                _sportsWorldContext.Athletes.Remove(athlete);
                await _sportsWorldContext.SaveChangesAsync();
                return NoContent();
            }
            else
            {
                return NotFound();
            }
        }
        catch
        {
            return StatusCode(500);
        }
    }


}