<div align="center">
<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/logo.png?raw=true" alt="Project Logo">

<h1>Better Cushion Placement</h1>
</div>

### Cushions can be placed as follows:

<div style="display: flex; align-items: center;">
    <img width="256" style="margin-right: 10px;" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/cushion_stack.png?raw=true" alt="Vertical Pixel Grid Snap: On">
    <div>
        <h3 style="margin-bottom: 5px;">Holding <code>Shift</code></h3>
        <ul style="margin-top: 0;">
            <li>Stack a held cushion on one in the world by interacting with it (if it will have block support).</li>
        </ul>
    </div>
</div>
<div style="display: flex; align-items: center;">
    <img width="256" style="margin-right: 10px;" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/block_grid.png?raw=true" alt="Block Grid">
    <div>
        <h3 style="margin-bottom: 5px;">Holding <code>Shift</code></h3>
        <ul style="margin-top: 0;">
            <li>Place a cushion horizontally centered on the closest vertex of the block grid.</li>
        </ul>
    </div>
</div>
<div style="display: flex; align-items: center;">
    <img width="256" style="margin-right: 10px;" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/pixel_grid.png?raw=true" alt="Pixel Grid">
    <div>
        <h3 style="margin-bottom: 5px;">Holding <code>Control</code></h3>
        <ul style="margin-top: 0;">
            <li>Place a cushion horizontally centered on the closest vertex of the pixel grid.</li>
        </ul>
    </div>
</div>
<div style="display: flex; align-items: center;">
    <img width="256" style="margin-right: 10px;" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/off_grid.png?raw=true" alt="Off-Grid">
    <div>
        <h3 style="margin-bottom: 5px;">Holding <code>Shift</code> + <code>Control</code></h3>
        <ul style="margin-top: 0;">
            <li>Place a cushion horizontally centered exactly where clicked, completely off-grid.</li>
        </ul>
    </div>
</div>
<div style="display: flex; align-items: center;">
    <img width="256" style="margin-right: 10px;" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/block_center.png?raw=true" alt="Block Center">
    <div>
        <h3 style="margin-bottom: 5px;">Holding neither (<em>Vanilla behavior</em>)</h3>
        <ul style="margin-top: 0;">
            <li>Place a cushion horizontally centered on the closest block grid tile center.</li>
        </ul>
    </div>
</div>
<br>

---

Vanilla behavior is currently to vertically position cushions exactly where clicked, but this will likely be changed to vertically snapping them to the pixel grid.

<div style="position: relative; display: inline-block;">
    <div style="position: absolute; top: 10px; left: 10px; color: white; background-color: rgba(0, 0, 0, 0.6); padding: 5px 10px; border-radius: 4px; font-weight: bold; font-family: sans-serif;">
        Snapping: On
    </div>
    <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/vertical_pixel_grid_snap_on.png?raw=true" alt="Vertical Pixel Grid Snap: On">
</div>
<div style="position: relative; display: inline-block;">
    <div style="position: absolute; top: 10px; left: 10px; color: white; background-color: rgba(0, 0, 0, 0.6); padding: 5px 10px; border-radius: 4px; font-weight: bold; font-family: sans-serif;">
        Snapping: Off
    </div>
    <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/vertical_pixel_grid_snap_off.png?raw=true" alt="Vertical Pixel Grid Snap: Off">
</div>

### Vertical pixel grid snapping of cushions can be enabled/disabled by a game rule:
- ID
  - `bettercushionplacement:snap_cushion_elevation_to_pixel_grid`
- Name
    - Snap Cushion Elevation To Pixel Grid 
- Description
  - Causes cushions placed on the inner walls of blocks like cauldrons to snap to the nearest pixel.
- Default
  - Off