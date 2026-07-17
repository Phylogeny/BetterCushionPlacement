<div align="center">
<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/logo.png?raw=true" alt="Project Logo">

<h1>Better Cushion Placement</h1>
</div>

## Cushions can be placed as follows:

| Click: block                                                                                                                            | Click: cushion                                                                                                                                               |
|-----------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/block_grid.png?raw=true" alt="Block Grid"> | <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/cushion_stack.png?raw=true" alt="Vertical Pixel Grid Snap: On"> |

### Holding <code>Shift</code>
- Place a cushion horizontally centered on the closest vertex of the block grid.
- Stack a held cushion on one in the world by interacting with it (if it will have block support).

---

<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/pixel_grid.png?raw=true" alt="Pixel Grid">

### Holding <code>Control</code>
- Place a cushion horizontally centered on the closest vertex of the pixel grid.

---

<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/off_grid.png?raw=true" alt="Off-Grid">

### Holding <code>Shift</code> + <code>Control</code>
- Place a cushion horizontally centered exactly where clicked, completely off-grid.

---

<img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/block_center.png?raw=true" alt="Block Center">

### Holding neither (<em>Vanilla behavior</em>)
- Place a cushion horizontally centered on the closest block grid tile center.

---
Vanilla block interaction behavior is as follows [BlockGetter_clipWithInteractionOverride](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/BlockGetter#L84-96):
- [L87](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/BlockGetter#L87) Raytrace a block's main shape.
- [L88](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/BlockGetter#L88) Check for hit.
   - [L89](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/BlockGetter#L89) If hit, raytrace the block's interaction shape.
   - [L90](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/BlockGetter#L90) Check for hit, and check if it's closer to the eyes than the main shape hit.
      - [L91](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/BlockGetter#L91) If the interaction shape hit is closer than main shape hit, substitute the direction of the hit result with the new direction.

Blocks by default have no interaction shape [BlockBehaviour_getInteractionShape](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/block/state/BlockBehaviour#L296-298), with the following exceptions:
- Cauldrons [getInteractionShape](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/block/AbstractCauldronBlock#L71-74)
- Composters [getInteractionShape](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/block/ComposterBlock#L102-105)
- Hoppers [getInteractionShape](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/block/HopperBlock#L72-75)
- Scaffolding [getInteractionShape](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/block/ScaffoldingBlock#L67-70)

Whether returning a full block shape or just the inner void shape, all of these blocks have interactions shapes with solid flat tops flush with the top of the block space.

| Composter interaction shape                                                                                                                                               | Hopper interaction shape                                                                                                                                            |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/composter_interaction_shape.png?raw=true" alt="Composter Interaction Shape"> | <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/hopper_interaction_shape.png?raw=true" alt="Hopper Interaction Shape"> |

The only way to get a raytrace hit on the inner walls of the shapes of these blocks is to get an even closer hit on the top face of their interaction shapes. This means that any inner hit will have the expected inner location vector, but with a direction of `Direction.UP`. So although cushions require interacting with an upward face [CushionItem_useOn#L38](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/item/CushionItem#L38), these blocks uniquely bypass this requirement.

While sub-pixel placement is still possible (on signs [WallSignBlock_SHAPES](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/block/WallSignBlock#L27), for example), placement on horizontal faces and arbitrary Y positioning was not intended. This is why Mojang added the `#cushion_uses_collision_shape` block tag, which forces the use of the collision shape raytrace result [CushionItem_recalculateContextForSpecialCollisionShapes#L81-88](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/item/CushionItem#L81-88).

| Wall collision placement                                                                                                                                            | Soul Sand collision placement                                                                                                                                                 |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/wall_collision_placement.png?raw=true" alt="Wall Collision Placement"> | <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/soul_sand_collision_placement.png?raw=true" alt="Soul Sand Collision Placement"> |

Note that raytrace is from the eyes [L84](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/item/CushionItem#L84) to the slightly past the exiting hit result vector [L85-86](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/item/CushionItem#L85-86), and that hit failure defaults to the original hit result [L88](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/item/CushionItem#L88). This means that when a collision shape hit is closer to the eyes than the main shape hit, as with wall blocks [WallBlock_collisionShapes](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/block/WallBlock#L58), the collision shape will be used. But when the main shape hit is closer, as with soul sand blocks [SoulSandBlock_SHAPE](https://mcsrc.dev/1/26.3-snapshot-4/net/minecraft/world/level/block/SoulSandBlock#L13), the main shape will be used in spite of having the tag.

| Flush placement w/o datapack                                                                                                                                        | Close to flush via a sign                                                                                                                                                         |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/cushion_flush_with_block.png?raw=true" alt="Cushion Flush With Block"> | <img width="256" src="https://github.com/Phylogeny/BetterCushionPlacement/blob/readme-assets/cushion_almost_flush_with_block.png?raw=true" alt="Cushion Almost Flush With Block"> |

### Bypass block tags for inner wall sub-pixel placement with a game rule:
- ID
  - `bettercushionplacement:allow_inner_wall_cushion_placement`
- Name
  - Allow Inner Wall Cushion Placement
- Description
  - Ignores `#cushion_uses_collision_shape` block tags, thus allowing sub-pixel cushion placement on the inner walls of cauldrons, composters, and hoppers without a data pack.
- Default
  - Off

---
### If this mod is installed on a server, but not a client, everything will work as intended, with the following purely visual exceptions:
1. The player's hand will swing
   - Even when cushion placement fails due to intersection with an existing cushion.
2. The player's hand will not swing
   - When placing a cushion on the inner wall of a cauldron, composter, or hopper.
   - When stacking a cushion on another cushion.