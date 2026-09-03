using Microsoft.AspNetCore.Mvc;
using SportsWorldAPI.Context;
using SportsWorldAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace SportsWorldAPI.Controllers;

[ApiController]
[Route("api/[controller]")]
public class VenueController(SportsWorldContext _sportsWorldContext) : ControllerBase
{
    [HttpGet]
    public async Task<ActionResult<List<Venue>>> Get()
    {
        try
        {
            List<Venue> venues = await _sportsWorldContext.Venues.ToListAsync();
            return Ok(venues);
        }
        catch
        {
            return StatusCode(500);
        }
    }
    [HttpGet("{id}")]
    public async Task<ActionResult<Venue>> Get(int id)
    {
        try
        {
            Venue? venue = await _sportsWorldContext.Venues.FindAsync(id);

            if (venue != null)
            {
                return Ok(venue);
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
    public async Task<ActionResult<Venue>> Post(Venue venue)
    {
        try
        {
            if (venue != null)
            {
                _sportsWorldContext.Venues.Add(venue);
                await _sportsWorldContext.SaveChangesAsync();
                return CreatedAtAction("Get", new { id = venue.Id }, venue);
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
    public async Task<IActionResult> Put(Venue editedVenue)
    {
        try
        {
            _sportsWorldContext.Entry(editedVenue).State = EntityState.Modified;
            await _sportsWorldContext.SaveChangesAsync();
            return NoContent();
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
            Venue? venue = await  _sportsWorldContext.Venues.FindAsync(id);
            if (venue != null)
            {
                _sportsWorldContext.Venues.Remove(venue);
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
