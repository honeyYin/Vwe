ALTER TABLE Paper ADD COLUMN isDraft INT(1);
ALTER TABLE Paper MODIFY COLUMN channelId bigint(20) NULL;
UPDATE Paper set isDraft = 0;
