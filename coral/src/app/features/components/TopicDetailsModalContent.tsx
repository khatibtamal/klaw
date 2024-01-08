import { BorderBox, Box, Grid, StatusChip } from "@aivenio/aquarium";
import MonacoEditor from "@monaco-editor/react";
import fromPairs from "lodash/fromPairs";
import isEmpty from "lodash/isEmpty";
import { TopicRequest } from "src/domain/topic/topic-types";

interface DetailsModalContentProps {
  topicRequest?: TopicRequest;
}

const formatAdvancedConfig = (
  entries: TopicRequest["advancedTopicConfigEntries"]
) => {
  if (entries === undefined) {
    return "";
  }

  const entriesToFlatObject = fromPairs(
    entries.map((config) => [config.configKey, config.configValue])
  );

  const flatObjectToJsonString = JSON.stringify(entriesToFlatObject, null, 2);
  return flatObjectToJsonString;
};

const Label = ({ children }: { children: React.ReactNode }) => (
  <dt className="inline-block mb-2 typography-small-strong text-grey-60">
    {children}
  </dt>
);

const TopicDetailsModalContent = ({
  topicRequest,
}: DetailsModalContentProps) => {
  if (topicRequest === undefined) {
    return <div>Request not found.</div>;
  }

  const {
    environmentName,
    requestOperationType,
    topicname,
    description,
    topicpartitions,
    replicationfactor,
    advancedTopicConfigEntries,
    remarks,
    requestor,
    requesttimestring,
    teamname,
  } = topicRequest;

  const hasAdvancedConfig =
    advancedTopicConfigEntries?.length > 0 &&
    !isEmpty(advancedTopicConfigEntries);

  return (
    <Grid htmlTag={"dl"} cols={"2"} rowGap={"6"}>
      <Box.Flex flexDirection={"column"}>
        <Label>Environment</Label>
        <dd>
          <StatusChip status={"neutral"} text={environmentName} />
        </dd>
      </Box.Flex>
      <Box.Flex flexDirection={"column"}>
        <Label>Request type</Label>
        <dd>
          <StatusChip status={"neutral"} text={requestOperationType} />
        </dd>
      </Box.Flex>
      <Grid.Item xs={2}>
        <Box.Flex flexDirection={"column"}>
          <Label>Topic</Label>
          <dd>{topicname}</dd>
        </Box.Flex>
      </Grid.Item>
      {/*getTopicRequestsForApprover for a CLAIM request*/}
      {/*does not contain topicpartitions, replicationfactor and description*/}
      {/*even though the openapi definition implies it*/}
      {/*In case it's a CLAIM request we don't want to show those options.*/}
      {requestOperationType !== "CLAIM" && (
        <>
          <Grid.Item xs={2}>
            <Box.Flex flexDirection={"column"}>
              <Label>Topic description</Label>
              <dd>{description}</dd>
            </Box.Flex>
          </Grid.Item>

          <Box.Flex flexDirection={"column"}>
            <Label>Topic partition</Label>
            <dd>{topicpartitions}</dd>
          </Box.Flex>

          <Box.Flex flexDirection={"column"}>
            <Label>Topic replication factor</Label>
            <dd>{replicationfactor}</dd>
          </Box.Flex>
        </>
      )}
      {hasAdvancedConfig && (
        <Grid.Item xs={2}>
          <Box.Flex flexDirection={"column"}>
            <Label>Advanced configuration</Label>
            <BorderBox borderColor={"grey-20"}>
              <MonacoEditor
                data-testid="topic-advanced-config"
                language="json"
                height={"100px"}
                theme={"light"}
                value={formatAdvancedConfig(advancedTopicConfigEntries)}
                options={{
                  ariaLabel: "Advanced configuration",
                  readOnly: true,
                  domReadOnly: true,
                  renderControlCharacters: false,
                  minimap: { enabled: false },
                  folding: false,
                  lineNumbers: "off",
                  scrollBeyondLastLine: false,
                }}
              />
            </BorderBox>
          </Box.Flex>
        </Grid.Item>
      )}
      <Grid.Item xs={2}>
        <Box.Flex flexDirection={"column"}>
          <Label>Message for approval</Label>
          <dd>{remarks || <i>No message</i>}</dd>
        </Box.Flex>
      </Grid.Item>
      <Box.Flex flexDirection={"column"}>
        <Label>Requested by</Label>
        <dd>{requestor}</dd>
      </Box.Flex>
      {requestOperationType === "CLAIM" && (
        <Box.Flex flexDirection={"column"}>
          <Label>Team</Label>
          <dd>{teamname}</dd>
        </Box.Flex>
      )}
      <Box.Flex flexDirection={"column"}>
        <Label>Requested on</Label>
        <dd>{requesttimestring} UTC</dd>
      </Box.Flex>
    </Grid>
  );
};

export default TopicDetailsModalContent;
