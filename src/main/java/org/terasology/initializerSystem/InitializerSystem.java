/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.managerInterface;

import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.holdingSystem.components.HoldingComponent;
import org.terasology.logic.inventory.InventoryComponent;
import org.terasology.logic.inventory.InventoryManager;
import org.terasology.logic.players.event.OnPlayerSpawnedEvent;
import org.terasology.registry.In;
import org.terasology.rendering.nui.NUIManager;
import org.terasology.MooConstants;
import org.terasology.world.block.BlockManager;
import org.terasology.world.block.items.BlockItemFactory;

@RegisterSystem(RegisterMode.AUTHORITY)
public class InitializerSystem extends BaseComponentSystem {

    @In
    private NUIManager nuiManager;

    @In
    private EntityManager entityManager;

    @In
    private InventoryManager inventoryManager;

    @In
    private BlockManager blockManager;

    @ReceiveEvent
    public void onPlayerSpawn(OnPlayerSpawnedEvent event, EntityRef player, InventoryComponent inventory) {
        BlockItemFactory blockItemFactory = new BlockItemFactory(entityManager);
        inventoryManager.giveItem(player, player, entityManager.create(MooConstants.SELECTION_TOOL_PREFAB));
        inventoryManager.giveItem(player, player, entityManager.create(MooConstants.BUILDING_UPGRADE_TOOL));
        inventoryManager.giveItem(player, player, blockItemFactory.newInstance(blockManager.getBlockFamily(MooConstants.PORTAL_PREFAB), 10));
        if (!player.hasComponent(HoldingComponent.class)) {
            player.addComponent(new HoldingComponent());
        }

        // TODO : Added for testing purposes, remove after adding some Oreon centric resources
        inventoryManager.giveItem(player, player, blockItemFactory.newInstance(blockManager.getBlockFamily("Core:Sand"), 99));
        inventoryManager.giveItem(player, player, blockItemFactory.newInstance(blockManager.getBlockFamily("Core:Dirt"), 99));

        inventoryManager.giveItem(player, player, entityManager.create(MooConstants.INITIATION_BOOK_PREFAB));
    }
}
